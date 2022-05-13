package com.checkout.apm.oxxo;

import com.checkout.EmptyResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Won't be supported anymore on further versions
 */
@Deprecated
public interface OxxoClient {

    CompletableFuture<EmptyResponse> succeed(String paymentId);

    CompletableFuture<EmptyResponse> expire(String paymentId);

}
