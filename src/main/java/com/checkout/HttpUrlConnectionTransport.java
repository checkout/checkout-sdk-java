package com.checkout;

import com.checkout.common.CheckoutUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class HttpUrlConnectionTransport implements Transport {

    private final URI baseUri;

    public HttpUrlConnectionTransport(String baseUri) {
        this.baseUri = URI.create(baseUri);
    }

    @Override
    public CompletableFuture<Response> invoke(String httpMethod, String path, ApiCredentials apiCredentials, String jsonRequest, String idempotencyKey) {
        return CompletableFuture.supplyAsync(() -> {
            HttpURLConnection connection = null;
            try {
                HttpURLConnection httpUrlConnection = (HttpURLConnection) getRequestUrl(path).openConnection();
                connection = httpUrlConnection;
                httpUrlConnection.setRequestProperty("user-agent", "checkout-sdk-java/" + CheckoutUtils.getVersionFromManifest());
                httpUrlConnection.setRequestProperty("Accept", "application/json;charset=UTF-8");
                httpUrlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                httpUrlConnection.setRequestProperty("Authorization", apiCredentials.getAuthorizationHeader());
                if (idempotencyKey != null) {
                    httpUrlConnection.setRequestProperty("Cko-Idempotency-Key", idempotencyKey);
                }
                httpUrlConnection.setRequestMethod(httpMethod);
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);

                log.info("Request: " + httpUrlConnection.getRequestProperties().toString());


                try {
                    if (jsonRequest != null) {
                        try (OutputStream os = httpUrlConnection.getOutputStream()) {
                            os.write(jsonRequest.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        switch (httpMethod) {
                            case "POST":
                                try (OutputStream os = httpUrlConnection.getOutputStream()) {
                                    os.write("".getBytes(StandardCharsets.UTF_8));
                                }
                                break;
                            case "GET":
                                httpUrlConnection.connect();
                                break;
                        }
                    }

                    log.info(httpMethod + " " + httpUrlConnection.getURL());

                    httpUrlConnection.connect();

                    log.info("Response: " + httpUrlConnection.getHeaderFields().toString());

                    final int statusCode = httpUrlConnection.getResponseCode();
                    final String requestId = httpUrlConnection.getHeaderFields().getOrDefault("Cko-Request-Id", Collections.singletonList("NO_REQUEST_ID_SUPPLIED")).get(0);

                    if (statusCode != 404) {
                        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((
                                httpUrlConnection.getErrorStream() == null ? httpUrlConnection.getInputStream() : httpUrlConnection.getErrorStream()
                        )))) {
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    private URL getRequestUrl(String path) {
        try {
            return baseUri.resolve(path).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
