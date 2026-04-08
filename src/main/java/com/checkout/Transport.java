package com.checkout;

import com.checkout.common.AbstractFileRequest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Transport {

    CompletableFuture<Response> invoke(ClientOperation clientOperation, String path, SdkAuthorization authorization, Object requestObject, String idempotencyKey, Map<String, String> queryParams);

    CompletableFuture<Response> invoke(ClientOperation clientOperation, String path, SdkAuthorization authorization, Object requestObject, String idempotencyKey, Map<String, String> queryParams, IHeaders headers);

    CompletableFuture<Response> submitFile(String path, SdkAuthorization authorization, AbstractFileRequest fileRequest);

    Response invokeSync(ClientOperation clientOperation, String path, SdkAuthorization authorization, Object requestObject, String idempotencyKey, Map<String, String> queryParams);

    Response invokeSync(ClientOperation clientOperation, String path, SdkAuthorization authorization, Object requestObject, String idempotencyKey, Map<String, String> queryParams, IHeaders headers);

    Response submitFileSync(String path, SdkAuthorization authorization, AbstractFileRequest fileRequest);

}
