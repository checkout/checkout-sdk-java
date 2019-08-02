package com.checkout.sample.spring;

import com.checkout.ApiCredentials;
import com.checkout.Transport;
import com.checkout.common.CheckoutUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class RestTemplateTransport implements Transport {

    private static final RestTemplate DEFAULT_REST_TEMPLATE = new RestTemplate();

    static {
        DEFAULT_REST_TEMPLATE.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    private final URI baseUri;
    private final RestTemplate restTemplate;

    public RestTemplateTransport(String baseUri) {
        this(baseUri, DEFAULT_REST_TEMPLATE);
    }

    public RestTemplateTransport(String baseUri, RestTemplate restTemplate) {
        this.baseUri = URI.create(baseUri);
        this.restTemplate = restTemplate;
    }

    @Override
    public CompletableFuture<Response> invoke(String httpMethod, String path, ApiCredentials apiCredentials, String jsonRequest, String idempotencyKey) {
        RequestEntity.BodyBuilder requestBuilder = RequestEntity
                .method(HttpMethod.resolve(httpMethod), getRequestUri(path))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .acceptCharset(StandardCharsets.UTF_8)
                .header("user-agent", "checkout-sdk-java/" + CheckoutUtils.getVersionFromManifest())
                .header("Authorization", apiCredentials.getAuthorizationHeader());
        if (idempotencyKey != null) {
            requestBuilder = requestBuilder.header("Ck-Idempotency-Key", idempotencyKey);
        }
        RequestEntity<?> request;
        if (jsonRequest != null) {
            request = requestBuilder.body(jsonRequest);
        } else {
            request = requestBuilder.build();
        }

        return CompletableFuture.supplyAsync(() -> {
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);
            return new Response(response.getStatusCodeValue(), response.getBody(), extractRequestId(response));
        });
    }

    private String extractRequestId(ResponseEntity<String> response) {
        return Optional.ofNullable(response.getHeaders().get("Ck-Request-Id"))
                .flatMap(list -> list.stream().findFirst())
                .orElse("NO_REQUEST_ID_SUPPLIED");
    }

    private URI getRequestUri(String path) {
        return baseUri.resolve(path);
    }
}
