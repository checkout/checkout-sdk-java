package com.checkout.apm.pagofacil;

import java.util.concurrent.CompletableFuture;

public interface PagoFacilClient {

    CompletableFuture<Void> succeed(String paymentId);

    CompletableFuture<Void> expire(String paymentId);

}
