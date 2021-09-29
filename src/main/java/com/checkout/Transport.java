package com.checkout;

import com.checkout.client.ClientOperation;
import com.checkout.common.AbstractFileRequest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Transport {

    CompletableFuture<Response> invoke(ClientOperation clientOperation, String path, SdkAuthorization authorization, String jsonRequest, String idempotencyKey);

    CompletableFuture<Response> invokeQuery(String path, SdkAuthorization authorization, Map<String, String> queryParams);

    CompletableFuture<Response> submitFile(String path, SdkAuthorization authorization, AbstractFileRequest fileRequest);

}
