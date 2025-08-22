package com.checkout;

import static com.checkout.ClientOperation.DELETE;
import static com.checkout.ClientOperation.GET;
import static com.checkout.ClientOperation.PATCH;
import static com.checkout.ClientOperation.POST;
import static com.checkout.ClientOperation.PUT;
import static com.checkout.ClientOperation.QUERY;
import static com.checkout.common.CheckoutUtils.validateParams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.checkout.common.AbstractFileRequest;
import com.checkout.common.CheckoutUtils;
import com.google.gson.reflect.TypeToken;
import org.apache.http.NameValuePair;

public class ApiClientImpl implements ApiClient {

    private static final String AUTHORIZATION = "authorization";
    private static final String PATH = "path";
    private final Serializer serializer;
    private final Transport transport;

    public ApiClientImpl(final CheckoutConfiguration configuration, final UriStrategy uriStrategy) {
        this.serializer = new GsonSerializer();
        this.transport = new ApacheHttpClientTransport(uriStrategy.getUri(), configuration.getHttpClientBuilder(), configuration.getExecutor(), configuration.getTransportConfiguration(), configuration);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> getAsync(final String path, final SdkAuthorization authorization, final Class<T> responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(GET, path, authorization, null, null, responseType);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> getAsync(final String path, final SdkAuthorization authorization, final Type responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(GET, path, authorization, null, null, responseType);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> putAsync(final String path, final SdkAuthorization authorization, final Class<T> responseType, final Object request) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(PUT, path, authorization, request, null, responseType);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> patchAsync(final String path, final SdkAuthorization authorization, final Class<T> responseType, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(PATCH, path, authorization, request, idempotencyKey, responseType);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> patchAsync(final String path, final SdkAuthorization authorization, final Type type, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "type", type, "request", request);
        return sendRequestAsync(PATCH, path, authorization, request, idempotencyKey, type);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> postAsync(final String path, final SdkAuthorization authorization, final Class<T> responseType, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(POST, path, authorization, request, idempotencyKey, responseType);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> postAsync(final String path, final SdkAuthorization authorization, final Type responseType, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(POST, path, authorization, request, idempotencyKey, responseType);
    }

    @Override
    public CompletableFuture<EmptyResponse> deleteAsync(final String path, final SdkAuthorization authorization) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(DELETE, path, authorization, null, null, EmptyResponse.class);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> deleteAsync(String path, SdkAuthorization authorization, Class<T> responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization);
        return sendRequestAsync(DELETE, path, authorization, null, null, responseType);
    }

    @Override
    public CompletableFuture<? extends HttpMetadata> postAsync(final String path, final SdkAuthorization authorization, final Map<Integer, Class<? extends HttpMetadata>> resultTypeMappings, final Object request, final String idempotencyKey) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "resultTypeMappings", resultTypeMappings);
        return transport.invoke(POST, path, authorization, serializer.toJson(request), idempotencyKey, null)
                .thenApply(this::errorCheck)
                .thenApply(response -> {
                    final Class<? extends HttpMetadata> responseType = resultTypeMappings.get(response.getStatusCode());
                    if (responseType == null) {
                        throw new IllegalStateException("The status code " + response.getStatusCode() + " is not mapped to a result type");
                    }
                    return deserialize(response, responseType);
                });
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> queryAsync(final String path,
                                                                    final SdkAuthorization authorization,
                                                                    final Object filter,
                                                                    final Class<T> responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "filter", filter);
        final Map<String, String> params = serializer.fromJson(serializer.toJson(filter),
                new TypeToken<Map<String, String>>() {
                }.getType());
        return transport.invoke(QUERY, path, authorization, null, null, params)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    @Override
    public CompletableFuture<ContentResponse> queryCsvContentAsync(final String path,
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
        return transport.invoke(QUERY, path, authorization, null, null, params)
                .thenApply(this::errorCheck)
                .thenApply(body -> transform(processAndGetContent(targetFile, body), body));
    }

    @SuppressWarnings("squid:S3516")
    private ContentResponse processAndGetContent(final String targetFile, final Response response) {
        final String content = response.getBody();
        if (StringUtils.isBlank(targetFile) || StringUtils.isBlank(content)) {
            return new ContentResponse(content);
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
        return new ContentResponse(content);
    }

    @Override
    public <T extends HttpMetadata> CompletableFuture<T> submitFileAsync(final String path, final SdkAuthorization authorization,
                                                                         final AbstractFileRequest request, final Class<T> responseType) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "fileRequest", request);
        return transport.submitFile(path, authorization, request)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    private <T extends HttpMetadata> CompletableFuture<T> sendRequestAsync(final ClientOperation clientOperation, final String path, final SdkAuthorization authorization, final Object request, final String idempotencyKey, final Type responseType) {
        return transport.invoke(clientOperation, path, authorization, request == null ? null : serializer.toJson(request), idempotencyKey, null)
                .thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    public <T extends HttpMetadata> CompletableFuture<T> postFormUrlEncodedAsync(
            final String path,
            final SdkAuthorization authorization,
            final List<NameValuePair> formParams,
            final Class<T> responseType
    ) {
        validateParams(PATH, path, AUTHORIZATION, authorization, "formParams", formParams);

        final String body = formParams.stream()
                .map(p -> p.getName() + "=" + encode(p.getValue()))
                .collect(Collectors.joining("&"));

        return transport.invoke(
                        ClientOperation.POST,
                        path,
                        authorization,
                        body,
                        null,
                        null,
                        "application/x-www-form-urlencoded"
                ).thenApply(this::errorCheck)
                .thenApply(response -> deserialize(response, responseType));
    }

    private String encode(final String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new CheckoutException("Failed to encode form param", e);
        }
    }

    private Response errorCheck(final Response response) {
        if (!CheckoutUtils.isSuccessHttpStatusCode(response.getStatusCode())) {
            final Map<String, Object> errorDetails = serializer.fromJson(response.getBody());
            throw new CheckoutApiException(response.getStatusCode(), response.getHeaders(), errorDetails);
        }
        return response;
    }

    private <T extends HttpMetadata> T deserialize(final Response response, final Class<T> responseType) {
        T result = serializer.fromJson(response.getBody(), responseType);
        //Unfortunate but GSON returns null if body is null, we need to instantiate the class type parameter for adding metadata
        if (result == null) {
            result = getInstanceFromT(responseType);
        }
        return transform(result, response);
    }

    private <T extends HttpMetadata> T deserialize(final Response response, final Type responseType) {
        T result = serializer.fromJson(response.getBody(), responseType);
        //Unfortunate but GSON returns null if body is null, we need to instantiate type parameter for adding metadata
        if (result == null) {
            result = getInstanceFromT(responseType);
        }
        return transform(result, response);

    }

    private <T extends HttpMetadata> T getInstanceFromT(final Type responseType) {
        try {
            final Class<?> aClass;
            if (responseType instanceof ParameterizedType) {
                final ParameterizedType type = (ParameterizedType) responseType;
                aClass = Class.forName(type.getRawType().getTypeName());
            } else {
                aClass = Class.forName(responseType.getTypeName());
            }
            return (T) aClass.newInstance();
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new CheckoutException(e);
        }
    }

    private <T extends HttpMetadata> T transform(final T result, final Response response) {
        result.setBody(response.getBody());
        result.setHttpStatusCode(response.getStatusCode());
        result.setResponseHeaders(response.getHeaders());
        return result;
    }

}
