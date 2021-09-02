package com.checkout.sample.spring;

import com.checkout.ApiCredentials;
import com.checkout.Response;
import com.checkout.Transport;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.FileRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class RestTemplateTransport implements Transport {

    private static final RestTemplate DEFAULT_REST_TEMPLATE = new RestTemplate();
    private static final String USER_AGENT = "user-agent";
    private static final String AUTHORIZATION = "Authorization";
    private static final String USER_AGENT_VALUE = "checkout-sdk-java/";

    static {
        DEFAULT_REST_TEMPLATE.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    private final URI baseUri;
    private final RestTemplate restTemplate;

    public RestTemplateTransport(final String baseUri) {
        this(baseUri, DEFAULT_REST_TEMPLATE);
    }

    public RestTemplateTransport(final String baseUri, final RestTemplate restTemplate) {
        this.baseUri = URI.create(baseUri);
        this.restTemplate = restTemplate;
    }

    @Override
    public CompletableFuture<Response> invoke(final String httpMethod, final String path, final ApiCredentials apiCredentials, final String jsonRequest, final String idempotencyKey) {
        validateParams("httpMethod", httpMethod, "path", path, "apiCredentials", apiCredentials, "jsonRequest", jsonRequest);
        RequestEntity.BodyBuilder requestBuilder = RequestEntity
                .method(HttpMethod.resolve(httpMethod), getRequestUri(path))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .acceptCharset(StandardCharsets.UTF_8)
                .header(USER_AGENT, USER_AGENT_VALUE + CheckoutUtils.getVersionFromManifest())
                .header(AUTHORIZATION, apiCredentials.getAuthorizationHeader());
        if (idempotencyKey != null) {
            requestBuilder = requestBuilder.header("Cko-Idempotency-Key", idempotencyKey);
        }
        final RequestEntity<?> request;
        if (jsonRequest != null) {
            request = requestBuilder.body(jsonRequest);
        } else {
            request = requestBuilder.build();
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                final ResponseEntity<String> response = restTemplate.exchange(request, String.class);
                return new Response(response.getStatusCodeValue(), response.getBody(), extractRequestId(response.getHeaders()));
            } catch (final HttpStatusCodeException e) {
                return new Response(e.getRawStatusCode(), e.getResponseBodyAsString(), extractRequestId(e.getResponseHeaders()));
            }
        });
    }

    @Override
    public CompletableFuture<Response> invokeQuery(final String path, final ApiCredentials apiCredentials, final Map<String, String> queryParams) {
        validateParams("path", path, "apiCredentials", apiCredentials, "queryParams", queryParams);
        final String queryPath = path + "?queryParameter= {queryParameter}";
        final RequestEntity<?> request = RequestEntity
                .method(HttpMethod.GET, getRequestUri(queryPath))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .acceptCharset(StandardCharsets.UTF_8)
                .header(USER_AGENT, USER_AGENT_VALUE + CheckoutUtils.getVersionFromManifest())
                .header(AUTHORIZATION, apiCredentials.getAuthorizationHeader())
                .build();
        return CompletableFuture.supplyAsync(() -> {
            try {
                final ResponseEntity<String> response = restTemplate.exchange(queryPath, HttpMethod.GET, request, String.class, queryParams);
                return new Response(response.getStatusCodeValue(), response.getBody(), extractRequestId(response.getHeaders()));
            } catch (final HttpStatusCodeException e) {
                return new Response(e.getRawStatusCode(), e.getResponseBodyAsString(), extractRequestId(e.getResponseHeaders()));
            }
        });
    }

    @Override
    public CompletableFuture<Response> submitFile(final String path, final ApiCredentials apiCredentials, final FileRequest fileRequest) {
        validateParams("path", path, "apiCredentials", apiCredentials, "fileRequest", fileRequest);
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileRequest.getFile());
        body.add("purpose", fileRequest.getPurpose().getPurpose());
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        headers.add(USER_AGENT, USER_AGENT_VALUE + CheckoutUtils.getVersionFromManifest());
        headers.add(AUTHORIZATION, apiCredentials.getAuthorizationHeader());
        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return CompletableFuture.supplyAsync(() -> {
            try {
                final ResponseEntity<String> response = restTemplate.postForEntity(getRequestUri(path), requestEntity, String.class);
                return new Response(response.getStatusCodeValue(), response.getBody(), extractRequestId(response.getHeaders()));
            } catch (final HttpStatusCodeException e) {
                return new Response(e.getRawStatusCode(), e.getResponseBodyAsString(), extractRequestId(e.getResponseHeaders()));
            }
        });
    }

    private String extractRequestId(final HttpHeaders headers) {
        return Optional.ofNullable(headers.get("Ck-Request-Id"))
                .flatMap(list -> list.stream().findFirst())
                .orElse("NO_REQUEST_ID_SUPPLIED");
    }

    private URI getRequestUri(final String path) {
        return baseUri.resolve(path);
    }
}
