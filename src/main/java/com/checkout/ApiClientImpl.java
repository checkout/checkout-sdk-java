package com.checkout;

import com.checkout.common.ApiResponseInfo;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.ErrorResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.Resource;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.requiresNonBlank;
import static com.checkout.common.CheckoutUtils.validateMultipleRequires;

public class ApiClientImpl implements ApiClient {

    private static final int UNPROCESSABLE = 422;
    private static final int NOT_FOUND = 404;
    private static final String CREDENTIALS = "credentials";
    private static final String PATH = "path";

    private final Serializer serializer;
    private final Transport transport;

    public ApiClientImpl(CheckoutConfiguration configuration) {
        this(new GsonSerializer(), new ApacheHttpClientTransport(configuration.getUri(), configuration.getApacheHttpClientBuilder()));
    }

    public ApiClientImpl(Serializer serializer, Transport transport) {
        validateMultipleRequires(ImmutableMap.of("serializer", serializer, "transport", transport));
        this.serializer = serializer;
        this.transport = transport;
    }

    @Override
    public <T> CompletableFuture<T> getAsync(String path, ApiCredentials credentials, Class<T> responseType) {
        validateMultipleRequires(ImmutableMap.of(PATH, path, CREDENTIALS, credentials));
        return sendRequestAsync("GET", path, credentials, null, null, responseType);
    }

    @Override
    public <T> CompletableFuture<T> putAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request) {
        validateMultipleRequires(ImmutableMap.of(PATH, path, CREDENTIALS, credentials));
        return sendRequestAsync("PUT", path, credentials, request, null, responseType);
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String path, ApiCredentials credentials) {
        validateMultipleRequires(ImmutableMap.of(PATH, path, CREDENTIALS, credentials));
        return sendRequestAsync("DELETE", path, credentials, null, null, Void.class);
    }

    @Override
    public <T> CompletableFuture<T> postAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request, String idempotencyKey) {
        validateMultipleRequires(ImmutableMap.of(PATH, path, CREDENTIALS, credentials));
        return sendRequestAsync("POST", path, credentials, request, idempotencyKey, responseType);
    }

    @Override
    public <T> CompletableFuture<T> patchAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request, String idempotencyKey) {
        validateMultipleRequires(ImmutableMap.of(PATH, path, CREDENTIALS, credentials));
        return sendRequestAsync("PATCH", path, credentials, request, idempotencyKey, responseType);
    }

    @Override
    public CompletableFuture<? extends Resource> postAsync(String path, ApiCredentials credentials, Map<Integer, Class<? extends Resource>> resultTypeMappings, Object request, String idempotencyKey) {
        validateMultipleRequires(ImmutableMap.of(PATH, path, CREDENTIALS, credentials, "resultTypeMappings", resultTypeMappings));
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

    @Override
    public <T> CompletableFuture<T> queryAsync(final String path, final ApiCredentials credentials, final Object filter,
                                               final Class<T> responseType) {
        validateMultipleRequires(ImmutableMap.of(PATH, path, CREDENTIALS, credentials, "filter", filter));
        final Map<String, String> params = serializer.fromJson(serializer.toJson(filter),
                new TypeToken<Map<String, String>>() {}.getType());
        return transport.invokeQuery(path, credentials, params)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    @Override
    public <T> CompletableFuture<T> submitFileAsync(final String path, final ApiCredentials credentials,
                                                    final FileRequest request, final Class<T> responseType) {
        validateMultipleRequires(ImmutableMap.of(PATH, path, CREDENTIALS, credentials, "fileRequest", request));
        return transport.submitFile(path, credentials, request)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    private <T> CompletableFuture<T> sendRequestAsync(String httpMethod, String path, ApiCredentials credentials, Object request, String idempotencyKey, Class<T> responseType) {
        requiresNonBlank(PATH, path);
        return transport.invoke(httpMethod, path, credentials, request == null ? null : serializer.toJson(request), idempotencyKey)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    private Response errorCheck(Response response) {
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

    private <T> T deserialize(Response response, Class<T> responseType) {
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
