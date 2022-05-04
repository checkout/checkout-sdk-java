package com.checkout.apm.rapipago;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Won't be supported anymore on further versions
 */
@Deprecated
public interface RapiPagoClient {

    CompletableFuture<Void> succeed(String paymentId);

    CompletableFuture<Void> expire(String paymentId);

}
