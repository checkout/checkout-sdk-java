package com.checkout.apm.pagofacil;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Won't be supported anymore on further versions
 */
@Deprecated
public interface PagoFacilClient {

    CompletableFuture<Void> succeed(String paymentId);

    CompletableFuture<Void> expire(String paymentId);

}
