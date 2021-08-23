package com.checkout.apm.baloto;

import java.util.concurrent.CompletableFuture;

public interface BalotoClient {

    CompletableFuture<Void> succeed(String id);

    CompletableFuture<Void> expire(String id);

}
