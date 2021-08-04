package com.checkout;

import com.checkout.common.FileRequest;
import com.checkout.common.Resource;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ApiClient {

    <T> CompletableFuture<T> getAsync(String path, ApiCredentials credentials, Class<T> responseType);

    <T> CompletableFuture<T> getAsync(String path, ApiCredentials credentials, Type responseType);

    <T> CompletableFuture<T> putAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request);

    CompletableFuture<Void> deleteAsync(String path, ApiCredentials credentials);

    <T> CompletableFuture<T> postAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request, String idempotencyKey);

    <T> CompletableFuture<T> postAsync(String path, ApiCredentials credentials, Type responseType, Object request, String idempotencyKey);

    <T> CompletableFuture<T> patchAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request, String idempotencyKey);

    CompletableFuture<? extends Resource> postAsync(String path, ApiCredentials credentials, Map<Integer, Class<? extends Resource>> resultTypeMappings, Object request, String idempotencyKey);

    <T> CompletableFuture<T> queryAsync(String path, ApiCredentials credentials, Object filter, Class<T> responseType);

    <T> CompletableFuture<T> submitFileAsync(String path, ApiCredentials credentials, FileRequest request, Class<T> responseType);

}