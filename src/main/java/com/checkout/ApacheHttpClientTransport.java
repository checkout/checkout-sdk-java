package com.checkout;

import com.checkout.common.CheckoutUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
            CloseableHttpResponse response = null;
            HttpUriRequest request = null;
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
            request.setHeader("user-agent", "checkout-sdk-java/" + CheckoutUtils.getVersionFromManifest());
            request.setHeader("Accept", "application/json;charset=UTF-8");
            request.setHeader("Content-Type", "application/json;charset=UTF-8");
            request.setHeader("Authorization", apiCredentials.getAuthorizationHeader());
            if (idempotencyKey != null) {
                request.setHeader("Cko-Idempotency-Key", idempotencyKey);
            }

            log.info("Request: " + Arrays.toString(sanitiseHeaders(request.getAllHeaders())));

            try {
                if (jsonRequest != null && request instanceof HttpEntityEnclosingRequest) {
                    ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(jsonRequest, ContentType.APPLICATION_JSON));
                }

                log.info(httpMethod + " " + request.getURI());

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
        });
    }

    private String getRequestUrl(String path) {
        try {
            return baseUri.resolve(path).toURL().toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
