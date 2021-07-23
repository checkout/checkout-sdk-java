package com.checkout;

import com.checkout.common.ApiResponseInfo;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.ErrorResponse;
import com.checkout.common.Resource;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.requiresNonNull;

public class ApiClientImpl implements ApiClient {

    private static final int UNPROCESSABLE = 422;
    private static final int NOT_FOUND = 404;

    private final Serializer serializer;
    private final Transport transport;

    public ApiClientImpl(CheckoutConfiguration configuration) {
        this(new GsonSerializer(), new ApacheHttpClientTransport(configuration.getUri(), configuration.getApacheHttpClientBuilder()));
    }

    public ApiClientImpl(Serializer serializer, Transport transport) {
        requiresNonNull("serializer", serializer);
        requiresNonNull("transport", transport);
        this.serializer = serializer;
        this.transport = transport;
    }

    @Override
    public <T> CompletableFuture<T> getAsync(String path, ApiCredentials credentials, Class<T> responseType) {
        if (CheckoutUtils.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("path must not be null");
        }
        if (credentials == null) {
            throw new IllegalArgumentException("credentials must not be null");
        }

        return sendRequestAsync("GET", path, credentials, null, null, responseType);
    }

    @Override
    public <T> CompletableFuture<T> putAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request) {
        if (CheckoutUtils.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("path must not be null");
        }
        if (credentials == null) {
            throw new IllegalArgumentException("credentials must not be null");
        }

        return sendRequestAsync("PUT", path, credentials, request, null, responseType);
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String path, ApiCredentials credentials) {
        if (CheckoutUtils.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("path must not be null");
        }
        if (credentials == null) {
            throw new IllegalArgumentException("credentials must not be null");
        }

        return sendRequestAsync("DELETE", path, credentials, null, null, Void.class);
    }

    @Override
    public <T> CompletableFuture<T> postAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request, String idempotencyKey) {
        if (CheckoutUtils.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("path must not be null or blank");
        }
        if (credentials == null) {
            throw new IllegalArgumentException("credentials must not be null");
        }

        return sendRequestAsync("POST", path, credentials, request, idempotencyKey, responseType);
    }

    @Override
    public <T> CompletableFuture<T> patchAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request, String idempotencyKey) {
        if (CheckoutUtils.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("path must not be null or blank");
        }
        if (credentials == null) {
            throw new IllegalArgumentException("credentials must not be null");
        }

        return sendRequestAsync("PATCH", path, credentials, request, idempotencyKey, responseType);
    }

    @Override
    public CompletableFuture<? extends Resource> postAsync(String path, ApiCredentials credentials, Map<Integer, Class<? extends Resource>> resultTypeMappings, Object request, String idempotencyKey) {
        if (CheckoutUtils.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("path must not be null");
        }
        if (credentials == null) {
            throw new IllegalArgumentException("credentials must not be null");
        }
        if (resultTypeMappings == null) {
            throw new IllegalArgumentException("resultTypeMappings must not be null");
        }

        return transport.invoke("POST", path, credentials, serializer.toJson(request), idempotencyKey)
                .thenApply(this::errorCheck)
                .thenApply(response -> {
                    Class<? extends Resource> responseType = resultTypeMappings.get(response.getStatusCode());
                    if (responseType == null) {
                        throw new IllegalStateException("The status code " + response.getStatusCode() + " is not mapped to a result type");
                    }
                    return deserialize(response, responseType);
                });
    }

    private <T> CompletableFuture<T> sendRequestAsync(String httpMethod, String path, ApiCredentials credentials, Object request, String idempotencyKey, Class<T> responseType) {
        if (CheckoutUtils.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("path must not be null or empty");
        }

        return transport.invoke(httpMethod, path, credentials, request == null ? null : serializer.toJson(request), idempotencyKey)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    private Transport.Response errorCheck(Transport.Response response) {
        if (!CheckoutUtils.isSuccessHttpStatusCode(response.getStatusCode())) {
            switch (response.getStatusCode()) {
                case UNPROCESSABLE:
                    ErrorResponse error = serializer.fromJson(response.getBody(), ErrorResponse.class);
                    throw new CheckoutValidationException(error, new ApiResponseInfo(response.getStatusCode(), response.getRequestId()));
                case NOT_FOUND:
                    throw new CheckoutResourceNotFoundException(response.getRequestId());
                default:
                    throw new CheckoutApiException(new ApiResponseInfo(response.getStatusCode(), response.getRequestId()));
            }
        }
        return response;
    }

    private <T> T deserialize(Transport.Response response, Class<T> responseType) {
        T result = serializer.fromJson(response.getBody(), responseType);
        if (result instanceof Resource) {
            ((Resource) result).setApiResponseInfo(new ApiResponseInfo(response.getStatusCode(), response.getRequestId()));
        } else if (result instanceof Resource[]) {
            Arrays.stream(((Resource[]) result))
                    .forEach(element ->
                            element.setApiResponseInfo(new ApiResponseInfo(response.getStatusCode(), response.getRequestId()))
                    );
        }
        return result;
    }
}
