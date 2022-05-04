package com.checkout.apm.fawry;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated  Won't be supported anymore on further versions
 */
@Deprecated
public interface FawryClient {

    CompletableFuture<Void> approve(String reference);

    CompletableFuture<Void> cancel(String reference);

}
