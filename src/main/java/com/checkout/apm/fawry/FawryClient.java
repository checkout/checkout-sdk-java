package com.checkout.apm.fawry;

import com.checkout.EmptyResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Won't be supported anymore on further versions
 */
@Deprecated
public interface FawryClient {

    CompletableFuture<EmptyResponse> approve(String reference);

    CompletableFuture<EmptyResponse> cancel(String reference);

}
