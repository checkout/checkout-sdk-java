package com.checkout;

import com.checkout.common.Resource;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ApiClient {
    <T> CompletableFuture<T> getAsync(String path, ApiCredentials credentials, Class<T> responseType);

    <T> CompletableFuture<T> postAsync(String path, ApiCredentials credentials, Class<T> responseType, Object request, Optional<String> idempotencyKey);

    CompletableFuture<? extends Resource> postAsync(String path, ApiCredentials credentials, Map<Integer, Class<? extends Resource>> resultTypeMappings, Object request, Optional<String> idempotencyKey);
}