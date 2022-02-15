package com.checkout;

import com.checkout.common.ApiResponseInfo;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.FileRequest;
import com.checkout.common.Resource;
import com.checkout.marketplace.MarketplaceFileRequest;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.checkout.ClientOperation.DELETE;
import static com.checkout.ClientOperation.GET;
import static com.checkout.ClientOperation.PATCH;
import static com.checkout.ClientOperation.POST;
import static com.checkout.ClientOperation.PUT;
import static com.checkout.common.CheckoutUtils.validateParams;

class ApiClientImpl implements ApiClient {

    private static final String AUTHORIZATION = "authorization";
    private static final String PATH = "path";

    private final Serializer serializer;
    private final Transport transport;

    ApiClientImpl(final CheckoutConfiguration configuration) {
        this.serializer = new GsonSerializer();
        this.transport = new ApacheHttpClientTransport(configuration.getBaseUri(), configuration.getHttpClientBuilder(), configuration.getExecutor());
    }

    @Override
    public <T> CompletableFuture<T> getAsync(final String path, final SdkAuthorization authorization, final Class<T> responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(GET, path, authorization, null, null, responseType);
    }

    @Override
    public <T> CompletableFuture<T> getAsync(final String path, final SdkAuthorization authorization, final Type responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(GET, path, authorization, null, null, responseType);
    }

    @Override
    public <T> CompletableFuture<T> putAsync(final String path, final SdkAuthorization authorization, final Class<T> responseType, final Object request) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(PUT, path, authorization, request, null, responseType);
    }

    @Override
    public CompletableFuture<Void> deleteAsync(final String path, final SdkAuthorization authorization) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(DELETE, path, authorization, null, null, Void.class);
    }

    @Override
    public <T> CompletableFuture<T> postAsync(final String path, final SdkAuthorization authorization, final Class<T> responseType, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(POST, path, authorization, request, idempotencyKey, responseType);
    }

    @Override
    public <T> CompletableFuture<T> postAsync(final String path, final SdkAuthorization authorization, final Type responseType, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(POST, path, authorization, request, idempotencyKey, responseType);
    }

    @Override
    public <T> CompletableFuture<T> patchAsync(final String path, final SdkAuthorization authorization, final Class<T> responseType, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(PATCH, path, authorization, request, idempotencyKey, responseType);
    }

    @Override
    public <T> CompletableFuture<T> patchAsync(final String path, final SdkAuthorization authorization, final Type type, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "type", type, "request", request);
        return sendRequestAsync(PATCH, path, authorization, request, idempotencyKey, type);
    }

    @Override
    public CompletableFuture<? extends Resource> postAsync(final String path, final SdkAuthorization authorization, final Map<Integer, Class<? extends Resource>> resultTypeMappings, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "resultTypeMappings", resultTypeMappings);
        return transport.invoke(POST, path, authorization, serializer.toJson(request), idempotencyKey)
                .thenApply(this::errorCheck)
                .thenApply(response -> {
                    final Class<? extends Resource> responseType = resultTypeMappings.get(response.getStatusCode());
                    if (responseType == null) {
                        throw new IllegalStateException("The status code " + response.getStatusCode() + " is not mapped to a result type");
                    }
                    return deserialize(response, responseType);
                });
    }

    @Override
    public <T> CompletableFuture<T> queryAsync(final String path,
                                               final SdkAuthorization authorization,
                                               final Object filter,
                                               final Class<T> responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "filter", filter);
        final Map<String, String> params = serializer.fromJson(serializer.toJson(filter),
                new TypeToken<Map<String, String>>() {
                }.getType());
        return transport.invokeQuery(GET, path, authorization, params)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    @Override
    public CompletableFuture<String> queryCsvContentAsync(final String path,
                                                          final SdkAuthorization authorization,
                                                          final Object filter,
                                                          final String targetFile) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        Map<String, String> params = new HashMap<>();
        if (filter != null) {
            params = serializer.fromJson(serializer.toJson(filter),
                    new TypeToken<Map<String, String>>() {
                    }.getType());
        }
        return transport.invokeQuery(ClientOperation.GET_CSV_CONTENT, path, authorization, params)
                .thenApply(this::errorCheck)
                .thenApply(body -> processAndGetContent(targetFile, body));
    }

    @SuppressWarnings("squid:S3516")
    private String processAndGetContent(final String targetFile, final Response response) {
        final String content = response.getBody();
        if (StringUtils.isBlank(targetFile) || StringUtils.isBlank(content)) {
            return content;
        }
        final File file = new File(targetFile);
        if (!file.exists()) {
            try {
                final boolean ignore = file.createNewFile(); //NOSONAR
            } catch (final IOException e) {
                throw new CheckoutException(String.format("Failed creating file %s", targetFile));
            }
        }
        try {
            Files.copy(
                    new ByteArrayInputStream(content.getBytes()),
                    file.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (final IOException e) {
            throw new CheckoutException(String.format("Failed writing file %s", targetFile), e);
        }
        return content;
    }

    @Override
    public <T> CompletableFuture<T> submitFileAsync(final String path, final SdkAuthorization authorization,
                                                    final FileRequest request, final Class<T> responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "fileRequest", request);
        return transport.submitFile(path, authorization, request)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    @Override
    public <T> CompletableFuture<T> submitFileAsync(final FilesTransport filesTransport, final String path, final SdkAuthorization authorization, final MarketplaceFileRequest request, final Class<T> responseType) {
        validateParams("filesTransport", filesTransport, PATH, path, AUTHORIZATION, authorization, "fileRequest", request);
        return filesTransport.submitFile(path, authorization, request)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    private <T> CompletableFuture<T> sendRequestAsync(final ClientOperation clientOperation, final String path, final SdkAuthorization authorization, final Object request, final String idempotencyKey, final Type responseType) {
        return transport.invoke(clientOperation, path, authorization, request == null ? null : serializer.toJson(request), idempotencyKey)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    private Response errorCheck(final Response response) {
        if (!CheckoutUtils.isSuccessHttpStatusCode(response.getStatusCode())) {
            final Map<String, Object> errorDetails = serializer.fromJson(response.getBody());
            throw new CheckoutApiException(response.getRequestId(), response.getStatusCode(), errorDetails);
        }
        return response;
    }

    private <T> T deserialize(final Response response, final Class<T> responseType) {
        final T result = serializer.fromJson(response.getBody(), responseType);
        return transform(result, response);
    }

    private <T> T deserialize(final Response response, final Type responseType) {
        final T result = serializer.fromJson(response.getBody(), responseType);
        return transform(result, response);

    }

    private <T> T transform(final T result, final Response response) {
        if (result instanceof Resource) {
            ((Resource) result).setApiResponseInfo(ApiResponseInfo.fromResponse(response));
        } else if (result instanceof Resource[]) {
            Arrays.stream(((Resource[]) result)).forEach(element -> element.setApiResponseInfo(ApiResponseInfo.fromResponse(response)));
        }
        return result;
    }

}
