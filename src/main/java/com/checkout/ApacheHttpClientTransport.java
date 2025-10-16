package com.checkout;

import com.checkout.accounts.AccountsFileRequest;
import com.checkout.common.AbstractFileRequest;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.FileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.NoHttpResponseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.http.ContentType;

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

import static com.checkout.ClientOperation.POST;
import static com.checkout.common.CheckoutUtils.ACCEPT_JSON;
import static com.checkout.common.CheckoutUtils.PROJECT_NAME;
import static com.checkout.common.CheckoutUtils.getVersionFromManifest;
import static org.apache.hc.core5.http.HttpHeaders.ACCEPT;
import static org.apache.hc.core5.http.HttpHeaders.AUTHORIZATION;
import static org.apache.hc.core5.http.HttpHeaders.USER_AGENT;

@Slf4j
class ApacheHttpClientTransport implements Transport {

    private static final String ACCEPT_CSV = "text/csv";
    private static final String CKO_IDEMPOTENCY_KEY = "Cko-Idempotency-Key";
    private static final String FILE = "file";
    private static final String PURPOSE = "purpose";
    private static final String PATH = "path";

    private final URI baseUri;
    private final CloseableHttpClient httpClient;
    private final Executor executor;
    private final TransportConfiguration transportConfiguration;
    private final CheckoutConfiguration configuration;

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
    }

    @Override
    public CompletableFuture<Response> invoke(final ClientOperation clientOperation,
                                              final String path,
                                              final SdkAuthorization authorization,
                                              final String requestBody,
                                              final String idempotencyKey,
                                              final Map<String, String> queryParams) {
        return CompletableFuture.supplyAsync(() -> {
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
            return performCall(authorization, requestBody, request, clientOperation);
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

    private HttpEntity getMultipartFileEntity(final AbstractFileRequest abstractFileRequest) {
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.EXTENDED);
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
                                 final String requestBody,
                                 final HttpUriRequest request,
                                 final ClientOperation clientOperation) {
      try {
        log.info("{}: {}", clientOperation, request.getUri());
      } catch (URISyntaxException e) {
        throw new CheckoutException("Failed to parse URI", e);
      }
      request.setHeader(USER_AGENT, PROJECT_NAME + "/" + getVersionFromManifest());
        request.setHeader(ACCEPT, getAcceptHeader(clientOperation));
        request.setHeader(AUTHORIZATION, authorization.getAuthorizationHeader());

        String currentRequestId = UUID.randomUUID().toString();

        addTelemetryHeader(request, currentRequestId);

        long startTime = System.currentTimeMillis();

        log.info("Request: " + Arrays.toString(sanitiseHeaders(request.getHeaders())));
        if (requestBody != null) {
             request.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        }
        try (final CloseableHttpResponse response = httpClient.execute(request)) {
            long elapsed = System.currentTimeMillis() - startTime;
            final int statusCode = response.getCode();
            log.info("Response: " + statusCode + " " + Arrays.toString(response.getHeaders()));

            updateTelemetryData(currentRequestId, elapsed);

            final Map<String, String> headers = Arrays.stream(response.getHeaders())
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

    private String getRequestUrl(final String path) {
        try {
            return baseUri.resolve(path).toURL().toString();
        } catch (final MalformedURLException e) {
            throw new CheckoutException(e);
        }
    }
}
