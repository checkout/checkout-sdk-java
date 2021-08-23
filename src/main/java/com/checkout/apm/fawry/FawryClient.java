package com.checkout.apm.fawry;

import java.util.concurrent.CompletableFuture;

public interface FawryClient {

    CompletableFuture<Void> approve(String reference);

    CompletableFuture<Void> cancel(String reference);

}
