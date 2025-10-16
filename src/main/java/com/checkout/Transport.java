package com.checkout;

import com.checkout.common.AbstractFileRequest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Transport {

    CompletableFuture<Response> invoke(
            ClientOperation clientOperation,
            String path,
            SdkAuthorization authorization,
            String jsonRequest,
            String idempotencyKey,
            Map<String, String> queryParams
    );

    CompletableFuture<Response> invoke(
            ClientOperation clientOperation,
            String path,
            SdkAuthorization authorization,
            String requestBody,
            String idempotencyKey,
            Map<String, String> queryParams,
            String contentType
    );

    CompletableFuture<Response> submitFile(
            String path,
            SdkAuthorization authorization,
            AbstractFileRequest fileRequest
    );
}
