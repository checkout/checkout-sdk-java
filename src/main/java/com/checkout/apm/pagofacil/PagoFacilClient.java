package com.checkout.apm.pagofacil;

import com.checkout.EmptyResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Won't be supported anymore on further versions
 */
@Deprecated
public interface PagoFacilClient {

    CompletableFuture<EmptyResponse> succeed(String paymentId);

    CompletableFuture<EmptyResponse> expire(String paymentId);

}
