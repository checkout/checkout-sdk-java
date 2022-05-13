package com.checkout;

import com.checkout.common.AbstractFileRequest;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ApiClient {

    <T extends HttpMetadata> CompletableFuture<T> getAsync(String path, SdkAuthorization authorization, Class<T> responseType);

    <T extends HttpMetadata> CompletableFuture<T> getAsync(String path, SdkAuthorization authorization, Type responseType);

    <T extends HttpMetadata> CompletableFuture<T> putAsync(String path, SdkAuthorization authorization, Class<T> responseType, Object request);

    <T extends HttpMetadata> CompletableFuture<T> postAsync(String path, SdkAuthorization authorization, Class<T> responseType, Object request, String idempotencyKey);

    <T extends HttpMetadata> CompletableFuture<T> patchAsync(String path, SdkAuthorization authorization, Type type, Object request, String idempotencyKey);

    <T extends HttpMetadata> CompletableFuture<T> postAsync(String path, SdkAuthorization authorization, Type responseType, Object request, String idempotencyKey);

    <T extends HttpMetadata> CompletableFuture<T> patchAsync(String path, SdkAuthorization authorization, Class<T> responseType, Object request, String idempotencyKey);

    CompletableFuture<? extends HttpMetadata> postAsync(String path, SdkAuthorization authorization, Map<Integer, Class<? extends HttpMetadata>> resultTypeMappings, Object request, String idempotencyKey);

    CompletableFuture<EmptyResponse> deleteAsync(String path, SdkAuthorization authorization);

    <T extends HttpMetadata> CompletableFuture<T> queryAsync(String path, SdkAuthorization authorization, Object filter, Class<T> responseType);

    CompletableFuture<ContentResponse> queryCsvContentAsync(String path, SdkAuthorization authorization, Object filter, String targetFile);

    <T extends HttpMetadata> CompletableFuture<T> submitFileAsync(String path, SdkAuthorization authorization, AbstractFileRequest request, Class<T> responseType);

}
