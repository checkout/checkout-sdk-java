package com.checkout.apm.rapipago;

import java.util.concurrent.CompletableFuture;

public interface RapiPagoClient {

    CompletableFuture<Void> succeed(String paymentId);

    CompletableFuture<Void> expire(String paymentId);

}
