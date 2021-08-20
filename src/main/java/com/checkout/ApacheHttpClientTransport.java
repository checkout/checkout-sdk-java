package com.checkout;

import com.checkout.common.CheckoutUtils;
import com.checkout.common.FileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class ApacheHttpClientTransport implements Transport {

    public static final String ACCEPT_CSV = "text/csv";
    public static final String ACCEPT_JSON = "application/json;charset=UTF-8";
    public static final String AUTHORIZATION = "Authorization";
    private final URI baseUri;
    private final CloseableHttpClient httpClient;

    public ApacheHttpClientTransport(final String baseUri, final HttpClientBuilder httpClientBuilder) {
        this.baseUri = URI.create(baseUri);
        if (httpClientBuilder != null) {
            httpClient = httpClientBuilder.build();
        } else {
            httpClient = HttpClients.createDefault();
        }
    }

    private Header[] sanitiseHeaders(final Header[] headers) {
        return Arrays.stream(headers)
                .filter(it -> !it.getName().equals(AUTHORIZATION))
                .toArray(Header[]::new);
    }

    @Override
    public CompletableFuture<Response> invoke(final String httpMethod, final String path, final ApiCredentials apiCredentials, final String requestBody, final String idempotencyKey) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpUriRequest request;
            String accept = ACCEPT_JSON;
            switch (httpMethod) {
                case "GET":
                    request = new HttpGet(getRequestUrl(path));
                    break;
                case "PUT":
                    request = new HttpPut(getRequestUrl(path));
                    break;
                case "POST":
                    request = new HttpPost(getRequestUrl(path));
                    break;
                case "DELETE":
                    request = new HttpDelete(getRequestUrl(path));
                    break;
                case "PATCH":
                    request = new HttpPatch(getRequestUrl(path));
                    break;
                case "GET_FILE":
                    request = new HttpGet(getRequestUrl(path));
                    accept = ACCEPT_CSV;
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported HTTP Method: " + httpMethod);
            }
            log.info("{}:{}", httpMethod, request.getURI());
            request.setHeader(AUTHORIZATION, apiCredentials.getAuthorizationHeader());
            if (idempotencyKey != null) {
                request.setHeader("Cko-Idempotency-Key", idempotencyKey);
            }
            return performCall(apiCredentials, requestBody, request, accept);
        });
    }

    @Override
    public CompletableFuture<Response> invokeQuery(final String path, final ApiCredentials apiCredentials,
                                                   final Map<String, String> queryParams) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpUriRequest request;
            try {
                final List<NameValuePair> params = queryParams.entrySet().stream()
                        .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
                request = new HttpGet(new URIBuilder(getRequestUrl(path)).addParameters(params).build());
                log.info("GET:{}", request.getURI());
                request.setHeader(AUTHORIZATION, apiCredentials.getAuthorizationHeader());
                return performCall(apiCredentials, null, request, ACCEPT_JSON);
            } catch (final URISyntaxException e) {
                throw new CheckoutException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Response> submitFile(final String path, final ApiCredentials apiCredentials,
                                                  final FileRequest fileRequest) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpPost post = new HttpPost(getRequestUrl(path));
            final HttpEntity build = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("file", fileRequest.getFile(), fileRequest.getContentType(), fileRequest.getFile().getName())
                    .addTextBody("purpose", fileRequest.getPurpose().getPurpose(), ContentType.DEFAULT_BINARY).build();
            post.setEntity(build);
            return performCall(apiCredentials, null, post, ACCEPT_JSON);
        });
    }

    private Response performCall(final ApiCredentials apiCredentials, final String requestBody, final HttpUriRequest request, final String accept) {
        CloseableHttpResponse response = null;
        request.setHeader("user-agent", "checkout-sdk-java/" + CheckoutUtils.getVersionFromManifest());
        request.setHeader("Accept", accept);
        request.setHeader(AUTHORIZATION, apiCredentials.getAuthorizationHeader());
        log.info("Request: " + Arrays.toString(sanitiseHeaders(request.getAllHeaders())));
        try {
            if (requestBody != null && request instanceof HttpEntityEnclosingRequest) {
                ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
            }
            response = httpClient.execute(request);
            log.info("Response: " + response.getStatusLine().getStatusCode() + " " + Arrays.toString(response.getAllHeaders()));
            final int statusCode = response.getStatusLine().getStatusCode();
            final String requestId = Optional.ofNullable(response.getFirstHeader("Cko-Request-Id")).map(Header::getValue).orElse("NO_REQUEST_ID_SUPPLIED");
            if (statusCode != HttpStatus.SC_NOT_FOUND && response.getEntity() != null && response.getEntity().getContent() != null) {
                if (ACCEPT_CSV.equalsIgnoreCase(accept)) {
                    createFile(requestBody, response);
                    return Response.builder()
                            .statusCode(statusCode)
                            .requestId(requestId)
                            .body(requestBody)
                            .build();
                }
                return Response.builder()
                        .statusCode(statusCode)
                        .requestId(requestId)
                        .body(EntityUtils.toString(response.getEntity()))
                        .build();
            }
            return Response.builder().statusCode(statusCode).requestId(requestId).build();
        } catch (final IOException e) {
            throw new CheckoutException(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (final IOException e) {
                    throw new CheckoutException(e);
                }
            }
        }
    }

    private String getRequestUrl(final String path) {
        try {
            return baseUri.resolve(path).toURL().toString();
        } catch (final MalformedURLException e) {
            throw new CheckoutException(e);
        }
    }

    private void createFile(final String targetFile, final CloseableHttpResponse response) throws IOException {
        final File file = new File(targetFile);
        Files.copy(
                response.getEntity().getContent(),
                file.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        if (!file.exists()) {
            throw new CheckoutException("Unable to create file=" + targetFile);
        }
    }
}
