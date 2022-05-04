package com.checkout.apm.oxxo;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Won't be supported anymore on further versions
 */
@Deprecated
public interface OxxoClient {

    CompletableFuture<Void> succeed(String paymentId);

    CompletableFuture<Void> expire(String paymentId);

}
