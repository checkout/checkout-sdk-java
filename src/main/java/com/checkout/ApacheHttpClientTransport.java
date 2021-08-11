package com.checkout;

import com.checkout.common.CheckoutUtils;
import com.checkout.common.FileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class ApacheHttpClientTransport implements Transport {

    private final URI baseUri;
    private final CloseableHttpClient httpClient;

    public ApacheHttpClientTransport(String baseUri, HttpClientBuilder httpClientBuilder) {
        this.baseUri = URI.create(baseUri);
        if (httpClientBuilder != null) {
            httpClient = httpClientBuilder.build();
        } else {
            httpClient = HttpClients.createDefault();
        }
    }

    private Header[] sanitiseHeaders(Header[] headers) {
        return Arrays.stream(headers)
                .filter(it -> !it.getName().equals("Authorization"))
                .toArray(Header[]::new);
    }

    @Override
    public CompletableFuture<Response> invoke(String httpMethod, String path, ApiCredentials apiCredentials, String jsonRequest, String idempotencyKey) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpUriRequest request;
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
                default:
                    throw new UnsupportedOperationException("Unsupported HTTP Method: " + httpMethod);
            }
            log.info("{}:{}", httpMethod, request.getURI());
            request.setHeader("Authorization", apiCredentials.getAuthorizationHeader());
            if (idempotencyKey != null) {
                request.setHeader("Cko-Idempotency-Key", idempotencyKey);
            }
            return performCall(apiCredentials, jsonRequest, request);
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
                request.setHeader("Authorization", apiCredentials.getAuthorizationHeader());
                return performCall(apiCredentials, null, request);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Response> submitFile(final String path, final ApiCredentials apiCredentials,
                                                  final FileRequest fileRequest) {
        return CompletableFuture.supplyAsync(() -> {
            HttpPost post = new HttpPost(getRequestUrl(path));
            HttpEntity build = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("file", fileRequest.getFile(), fileRequest.getContentType(), fileRequest.getFile().getName())
                    .addTextBody("purpose", fileRequest.getPurpose().getPurpose(), ContentType.DEFAULT_BINARY).build();
            post.setEntity(build);
            return performCall(apiCredentials, null, post);
        });
    }

    private Response performCall(final ApiCredentials apiCredentials, final String jsonRequest, final HttpUriRequest request) {
        CloseableHttpResponse response = null;
        request.setHeader("user-agent", "checkout-sdk-java/" + CheckoutUtils.getVersionFromManifest());
        request.setHeader("Accept", "application/json;charset=UTF-8");
        request.setHeader("Authorization", apiCredentials.getAuthorizationHeader());
        log.info("Request: " + Arrays.toString(sanitiseHeaders(request.getAllHeaders())));
        try {
            if (jsonRequest != null && request instanceof HttpEntityEnclosingRequest) {
                ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(jsonRequest, ContentType.APPLICATION_JSON));
            }
            response = httpClient.execute(request);
            log.info("Response: " + response.getStatusLine().getStatusCode() + " " + Arrays.toString(response.getAllHeaders()));
            final int statusCode = response.getStatusLine().getStatusCode();
            final String requestId = Optional.ofNullable(response.getFirstHeader("Cko-Request-Id"))
                    .map(Header::getValue).orElse("NO_REQUEST_ID_SUPPLIED");
            if (statusCode != 404) {
                if (response.getEntity() != null && response.getEntity().getContent() != null) {
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        String jsonBody = stringBuilder.toString();
                        return new Response(statusCode, jsonBody, requestId);
                    }
                } else {
                    return new Response(statusCode, null, requestId);
                }
            } else {
                return new Response(statusCode, null, requestId);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String getRequestUrl(String path) {
        try {
            return baseUri.resolve(path).toURL().toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
