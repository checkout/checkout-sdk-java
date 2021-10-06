package com.checkout.apm.ideal;

import java.util.concurrent.CompletableFuture;

public interface IdealClient {

    CompletableFuture<IdealInfo> getInfo();

    CompletableFuture<IssuerResponse> getIssuers();

}
