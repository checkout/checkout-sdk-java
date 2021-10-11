package com.checkout.apm.giropay;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated GiropayClient client will be removed in a future version.
 */
@Deprecated
public interface GiropayClient {

    @Deprecated
    CompletableFuture<BanksResponse> getEpsBanks();

    @Deprecated
    CompletableFuture<BanksResponse> getBanks();

}
