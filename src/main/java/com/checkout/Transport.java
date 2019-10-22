package com.checkout;

import lombok.Data;

import java.util.concurrent.CompletableFuture;

public interface Transport {
    CompletableFuture<Response> invoke(String httpMethod, String path, ApiCredentials apiCredentials, String jsonRequest, String idempotencyKey);

    @Data
    class Response {
        private final int statusCode;
        private final String body;
        private final String requestId;
    }
}
