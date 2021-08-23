package com.checkout.apm.giropay;

import java.util.concurrent.CompletableFuture;

public interface GiropayClient {

    CompletableFuture<BanksResponse> getEpsBanks();

    CompletableFuture<BanksResponse> getBanks();

}
