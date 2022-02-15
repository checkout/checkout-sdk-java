package com.checkout;

import com.checkout.common.FileRequest;
import com.checkout.common.Resource;
import com.checkout.marketplace.MarketplaceFileRequest;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ApiClient {

    <T> CompletableFuture<T> getAsync(String path, SdkAuthorization authorization, Class<T> responseType);

    <T> CompletableFuture<T> getAsync(String path, SdkAuthorization authorization, Type responseType);

    <T> CompletableFuture<T> putAsync(String path, SdkAuthorization authorization, Class<T> responseType, Object request);

    CompletableFuture<Void> deleteAsync(String path, SdkAuthorization authorization);

    <T> CompletableFuture<T> postAsync(String path, SdkAuthorization authorization, Class<T> responseType, Object request, String idempotencyKey);

    <T> CompletableFuture<T> postAsync(String path, SdkAuthorization authorization, Type responseType, Object request, String idempotencyKey);

    <T> CompletableFuture<T> patchAsync(String path, SdkAuthorization authorization, Class<T> responseType, Object request, String idempotencyKey);

    <T> CompletableFuture<T> patchAsync(String path, SdkAuthorization authorization, Type type, Object request, String idempotencyKey);

    CompletableFuture<? extends Resource> postAsync(String path, SdkAuthorization authorization, Map<Integer, Class<? extends Resource>> resultTypeMappings, Object request, String idempotencyKey);

    <T> CompletableFuture<T> queryAsync(String path, SdkAuthorization authorization, Object filter, Class<T> responseType);

    CompletableFuture<String> queryCsvContentAsync(String path, SdkAuthorization authorization, Object filter, String targetFile);

    <T> CompletableFuture<T> submitFileAsync(String path, SdkAuthorization authorization, FileRequest request, Class<T> responseType);

    <T> CompletableFuture<T> submitFileAsync(FilesTransport transport, String path, SdkAuthorization authorization, MarketplaceFileRequest request, Class<T> responseType);

}
