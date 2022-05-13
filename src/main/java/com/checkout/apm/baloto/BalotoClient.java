package com.checkout.apm.baloto;

import com.checkout.EmptyResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Won't be supported anymore on further versions
 */
@Deprecated
public interface BalotoClient {

    CompletableFuture<EmptyResponse> succeed(String id);

    CompletableFuture<EmptyResponse> expire(String id);

}
