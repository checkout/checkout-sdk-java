package com.checkout.apm.oxxo;

import java.util.concurrent.CompletableFuture;

public interface OxxoClient {

    CompletableFuture<Void> succeed(String paymentId);

    CompletableFuture<Void> expire(String paymentId);

}
