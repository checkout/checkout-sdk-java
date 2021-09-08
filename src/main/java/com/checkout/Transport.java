package com.checkout;

import com.checkout.client.ClientOperation;
import com.checkout.common.FileRequest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Transport {

    CompletableFuture<Response> invoke(ClientOperation clientOperation, String path, ApiCredentials apiCredentials, String jsonRequest, String idempotencyKey);

    CompletableFuture<Response> invokeQuery(String path, ApiCredentials apiCredentials, Map<String, String> queryParams);

    CompletableFuture<Response> submitFile(String path, ApiCredentials apiCredentials, FileRequest fileRequest);

}
