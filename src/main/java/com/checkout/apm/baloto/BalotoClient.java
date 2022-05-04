package com.checkout.apm.baloto;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Won't be supported anymore on further versions
 */
@Deprecated
public interface BalotoClient {

    CompletableFuture<Void> succeed(String id);

    CompletableFuture<Void> expire(String id);

}
