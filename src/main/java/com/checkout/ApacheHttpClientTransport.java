package com.checkout;

import com.checkout.accounts.AccountsFileRequest;
import com.checkout.common.AbstractFileRequest;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.FileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.function.Supplier;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;

import static com.checkout.ClientOperation.POST;
import static com.checkout.common.CheckoutUtils.ACCEPT_JSON;
import static com.checkout.common.CheckoutUtils.PROJECT_NAME;
import static com.checkout.common.CheckoutUtils.getVersionFromManifest;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.HttpHeaders.USER_AGENT;

@Slf4j
class ApacheHttpClientTransport implements Transport {

    private static final String ACCEPT_CSV = "text/csv";
    private static final String CKO_IDEMPOTENCY_KEY = "Cko-Idempotency-Key";
    private static final String FILE = "file";
    private static final String PURPOSE = "purpose";
    private static final String PATH = "path";
    private static final String INVALID_REQUEST_PATH = "Invalid request path";

    private final URI baseUri;
    private final CloseableHttpClient httpClient;
    private final Executor executor;
    private final TransportConfiguration transportConfiguration;
    private final CheckoutConfiguration configuration;
    private final Serializer serializer;

    private static final ThreadLocal<RequestMetrics> telemetryData = ThreadLocal.withInitial(RequestMetrics::new);

    ApacheHttpClientTransport(
            final URI baseUri,
            final HttpClientBuilder httpClientBuilder,
            final Executor executor,
            final TransportConfiguration transportConfiguration,
            final CheckoutConfiguration configuration
    ) {
        CheckoutUtils.validateParams("baseUri", baseUri, "httpClientBuilder", httpClientBuilder, "executor", executor);
        this.baseUri = baseUri;
        this.httpClient = httpClientBuilder
                .setRedirectStrategy(new CustomAwsRedirectStrategy())
                .build();
        this.executor = executor;
        this.transportConfiguration = transportConfiguration;
        this.configuration = configuration;
        this.serializer = new GsonSerializer();
    }

    @Override
    public CompletableFuture<Response> invoke(final ClientOperation clientOperation,
                                              final String path,
                                              final SdkAuthorization authorization,
                                              final Object requestObject,
                                              final String idempotencyKey,
                                              final Map<String, String> queryParams) {
        return invoke(clientOperation, path, authorization, requestObject, idempotencyKey, queryParams, null);
    }

    @Override
    public CompletableFuture<Response> invoke(final ClientOperation clientOperation,
                                              final String path,
                                              final SdkAuthorization authorization,
                                              final Object requestObject,
                                              final String idempotencyKey,
                                              final Map<String, String> queryParams,
                                              final IHeaders headers) {

        return CompletableFuture.supplyAsync(() -> {
            final HttpUriRequest request = buildRequest(clientOperation, path, queryParams, idempotencyKey);
            return performCall(authorization, requestObject, request, clientOperation, headers);
        }, executor);
    }

    @Override
    public CompletableFuture<Response> submitFile(final String path, final SdkAuthorization authorization, final AbstractFileRequest fileRequest) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpPost request = new HttpPost(getRequestUrl(path));
            request.setEntity(getMultipartFileEntity(fileRequest));
            return performCall(authorization, null, request, POST);
        }, executor);
    }

    @Override
    public Response invokeSync(final ClientOperation clientOperation,
                               final String path,
                               final SdkAuthorization authorization,
                               final Object requestObject,
                               final String idempotencyKey,
                               final Map<String, String> queryParams) {
        return invokeSync(clientOperation, path, authorization, requestObject, idempotencyKey, queryParams, null);
    }

    @Override
    public Response invokeSync(final ClientOperation clientOperation,
                               final String path,
                               final SdkAuthorization authorization,
                               final Object requestObject,
                               final String idempotencyKey,
                               final Map<String, String> queryParams,
                               final IHeaders headers) {
        final HttpUriRequest request = buildRequest(clientOperation, path, queryParams, idempotencyKey);

        final Supplier<Response> callSupplier = () -> performCall(authorization, requestObject, request, clientOperation, headers);
        return executeWithResilience4j(callSupplier);
    }

    @Override
    public Response submitFileSync(final String path, final SdkAuthorization authorization, final AbstractFileRequest fileRequest) {
        final HttpPost request = new HttpPost(getRequestUrl(path));
        request.setEntity(getMultipartFileEntity(fileRequest));

        final Supplier<Response> callSupplier = () -> performCall(authorization, null, request, POST);
        return executeWithResilience4j(callSupplier);
    }

    /**
     * Executes a supplier function with Resilience4j components (Circuit Breaker, Retry, Rate Limiter)
     * if they are configured. Otherwise, executes the supplier directly.
     */
    private Response executeWithResilience4j(final Supplier<Response> supplier) {
        final Resilience4jConfiguration resilience4jConfig = configuration.getResilience4jConfiguration();
        
        if (resilience4jConfig == null) {
            return supplier.get();
        }
        
        Supplier<Response> decoratedSupplier = supplier;
        
        // Apply Rate Limiter if configured
        if (resilience4jConfig.hasRateLimiter()) {
            decoratedSupplier = RateLimiter.decorateSupplier(resilience4jConfig.getRateLimiter(), decoratedSupplier);
        }
        
        // Apply Retry if configured
        if (resilience4jConfig.hasRetry()) {
            decoratedSupplier = Retry.decorateSupplier(resilience4jConfig.getRetry(), decoratedSupplier);
        }
        
        // Apply Circuit Breaker if configured (should be last to wrap everything)
        if (resilience4jConfig.hasCircuitBreaker()) {
            decoratedSupplier = CircuitBreaker.decorateSupplier(resilience4jConfig.getCircuitBreaker(), decoratedSupplier);
        }
        
        return decoratedSupplier.get();
    }

    private HttpEntity getMultipartFileEntity(final AbstractFileRequest abstractFileRequest) {
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (abstractFileRequest instanceof FileRequest) {
            final FileRequest fileRequest = (FileRequest) abstractFileRequest;
            builder.addBinaryBody(FILE, fileRequest.getFile(), fileRequest.getContentType(), fileRequest.getFile().getName())
                    .addTextBody(PURPOSE, fileRequest.getPurpose().getPurpose(), ContentType.DEFAULT_BINARY)
                    .build();
        } else if (abstractFileRequest instanceof AccountsFileRequest) {
            final AccountsFileRequest fileRequest = (AccountsFileRequest) abstractFileRequest;
            builder.addBinaryBody(PATH, fileRequest.getFile(), fileRequest.getContentType(), fileRequest.getFile().getName())
                    .addTextBody(PURPOSE, fileRequest.getPurpose().getPurpose(), ContentType.DEFAULT_TEXT)
                    .build();
        } else {
            throw new CheckoutException("Not supported request object");
        }
        return builder.build();
    }

    private Response performCall(final SdkAuthorization authorization,
                                 final Object requestBody,
                                 final HttpUriRequest request,
                                 final ClientOperation clientOperation) {
        return performCall(authorization, requestBody, request, clientOperation, null);
    }

    private Response performCall(final SdkAuthorization authorization,
                                 final Object requestBody,
                                 final HttpUriRequest request,
                                 final ClientOperation clientOperation,
                                 final IHeaders customHeaders) {
        log.info("{}: {}", clientOperation, request.getURI());
        request.setHeader(USER_AGENT, PROJECT_NAME + "/" + getVersionFromManifest());
        request.setHeader(ACCEPT, getAcceptHeader(clientOperation));
        request.setHeader(AUTHORIZATION, authorization.getAuthorizationHeader());

        addHeadersFromRequestBody(requestBody, request);
        applyHeaders(customHeaders, request);

        String currentRequestId = UUID.randomUUID().toString();

        addTelemetryHeader(request, currentRequestId);

        long startTime = System.currentTimeMillis();

        log.info("Request: " + Arrays.toString(sanitiseHeaders(request.getAllHeaders())));
        if (requestBody != null && request instanceof HttpEntityEnclosingRequest) {
            HttpEntity httpEntity = null;
            
            if (requestBody instanceof HttpEntity) {
                httpEntity = (HttpEntity) requestBody;
            } else if (requestBody instanceof MultipartEntityBuilder) {
                httpEntity = ((MultipartEntityBuilder) requestBody).build();
            } else if (requestBody instanceof UrlEncodedFormEntity) {
                // Ensure proper Content-Type header for form-encoded content
                request.setHeader(CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
                httpEntity = (UrlEncodedFormEntity) requestBody;
            } else {
                String json = serializer.toJson(requestBody);
                httpEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            }
            
            ((HttpEntityEnclosingRequestBase) request).setEntity(httpEntity);
        }

        try (final CloseableHttpResponse response = httpClient.execute(request)) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.info("Response: " + response.getStatusLine().getStatusCode() + " " + Arrays.toString(response.getAllHeaders()));

            updateTelemetryData(currentRequestId, elapsed);

            final int statusCode = response.getStatusLine().getStatusCode();
            final Map<String, String> headers = Arrays.stream(response.getAllHeaders())
                    .collect(Collectors.toMap(Header::getName, Header::getValue));

            if (statusCode != HttpStatus.SC_NOT_FOUND && response.getEntity() != null && response.getEntity().getContent() != null) {
                return Response.builder()
                        .statusCode(statusCode)
                        .body(EntityUtils.toString(response.getEntity()))
                        .headers(headers)
                        .build();
            }
            return Response.builder().statusCode(statusCode).headers(headers).build();
        } catch (final NoHttpResponseException e) {
            return handleException(e, "Target server failed to respond with a valid HTTP response.");
        } catch (final Exception e) {
            return handleException(e, "Exception occurred during the execution of the client...");
        }
    }

    private void addHeadersFromRequestBody(final Object requestBody, final HttpUriRequest request) {
        if (requestBody instanceof IHeaders) {
            applyHeaders((IHeaders) requestBody, request);
        }
    }

    private void applyHeaders(final IHeaders headers, final HttpUriRequest request) {
        if (headers == null) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.getHeaders().entrySet()) {
            request.setHeader(entry.getKey(), entry.getValue());
        }
    }

    private void addTelemetryHeader(HttpUriRequest request, String currentRequestId) {
        if (configuration.isTelemetryEnabled()) {
            String telemetryHeader = generateTelemetryHeader(currentRequestId);
            request.setHeader("cko-sdk-telemetry", telemetryHeader);
        }
    }

    private String generateTelemetryHeader(String currentRequestId) {
        RequestMetrics metrics = getTelemetryData();
        metrics.setRequestId(currentRequestId);
        return metrics.toTelemetryHeader();
    }

    private static void updateTelemetryData(String requestId, long duration) {
        RequestMetrics metrics = telemetryData.get();
        metrics.setPrevRequestId(requestId);
        metrics.setPrevRequestDuration(duration);
    }

    private static RequestMetrics getTelemetryData() {
        return telemetryData.get();
    }

    private Response handleException(Exception e, String errorMessage) {
        log.error(errorMessage, e);
        return Response.builder().statusCode(transportConfiguration.getDefaultHttpStatusCode()).build();
    }

    private Header[] sanitiseHeaders(final Header[] headers) {
        return Arrays.stream(headers)
                .filter(it -> !it.getName().equals(AUTHORIZATION))
                .filter(it -> !it.getName().equalsIgnoreCase("Signature"))
                .toArray(Header[]::new);
    }

    private String getAcceptHeader(final ClientOperation clientOperation) {
        switch (clientOperation) {
            case GET:
            case PUT:
            case POST:
            case DELETE:
            case PATCH:
            case QUERY:
                return ACCEPT_JSON;
            case GET_CSV_CONTENT:
                return ACCEPT_CSV;
            default:
                throw new IllegalStateException(String.format("Accept header not configured for client operation %s", clientOperation));
        }
    }

    private HttpUriRequest buildRequest(final ClientOperation clientOperation, final String path, final Map<String, String> queryParams, final String idempotencyKey) {
        final HttpUriRequest request;
        switch (clientOperation) {
            case GET:
            case GET_CSV_CONTENT:
                request = new HttpGet(getRequestUrl(path));
                break;
            case PUT:
                request = new HttpPut(getRequestUrl(path));
                break;
            case POST:
                request = new HttpPost(getRequestUrl(path));
                break;
            case DELETE:
                request = new HttpDelete(getRequestUrl(path));
                break;
            case PATCH:
                request = new HttpPatch(getRequestUrl(path));
                break;
            case QUERY:
                final List<NameValuePair> params = queryParams.entrySet().stream()
                        .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
                try {
                    request = new HttpGet(new URIBuilder(getRequestUrl(path)).addParameters(params).build());
                } catch (final URISyntaxException e) {
                    throw new CheckoutException(e);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported HTTP Method: " + clientOperation);
        }
        if (idempotencyKey != null) {
            request.setHeader(CKO_IDEMPOTENCY_KEY, idempotencyKey);
        }
        return request;
    }

    /**
     * Resolves a request path against the base URI. The path must be relative and made of non-empty
     * segments, none of them "." or ".." (also when percent-encoded or followed by ";"), with no "?" or
     * "#": query parameters are added separately. The resolved URL must keep the scheme, host, port and
     * base path of the base URI.
     *
     * @param path the request path, as built by {@code AbstractClient.buildPath}
     * @return the absolute request URL
     * @throws CheckoutArgumentException if the path is invalid or resolves outside the base URI
     */
    String getRequestUrl(final String path) {
        validateRequestPath(path);
        final URI resolved = baseUri.resolve(path);
        if (!isUnderBaseUri(resolved)) {
            throw new CheckoutArgumentException(INVALID_REQUEST_PATH);
        }
        try {
            return resolved.toURL().toString();
        } catch (final MalformedURLException e) {
            throw new CheckoutException(e);
        }
    }

    private static void validateRequestPath(final String path) {
        if (path == null || path.isEmpty() || path.indexOf('?') >= 0 || path.indexOf('#') >= 0) {
            throw new CheckoutArgumentException(INVALID_REQUEST_PATH);
        }
        for (final String segment : path.split("/", -1)) {
            if (isEmptyOrDotSegment(segment)) {
                throw new CheckoutArgumentException(INVALID_REQUEST_PATH);
            }
        }
    }

    private static boolean isEmptyOrDotSegment(final String segment) {
        final int parameters = segment.indexOf(';');
        final String name = (parameters >= 0 ? segment.substring(0, parameters) : segment).replaceAll("(?i)%2e", ".");
        return name.isEmpty() || ".".equals(name) || "..".equals(name);
    }

    /**
     * True when the URL has the scheme, host and port of the base URI and a path under the base URI's
     * directory, the part of its path that {@link URI#resolve(String)} keeps.
     */
    private boolean isUnderBaseUri(final URI resolved) {
        final String basePath = baseUri.getRawPath() == null ? "" : baseUri.getRawPath();
        final String baseDirectory = basePath.substring(0, basePath.lastIndexOf('/') + 1);
        return baseUri.getScheme().equalsIgnoreCase(resolved.getScheme())
                && baseUri.getHost() != null
                && baseUri.getHost().equalsIgnoreCase(resolved.getHost())
                && baseUri.getPort() == resolved.getPort()
                && resolved.getRawPath() != null
                && resolved.getRawPath().startsWith(baseDirectory)
                && resolved.getRawQuery() == null
                && resolved.getRawFragment() == null;
    }
}
