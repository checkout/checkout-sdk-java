package com.checkout.tokens;

import java.util.concurrent.CompletableFuture;

public interface TokensClient {

    CompletableFuture<CardTokenResponse> request(CardTokenRequest cardTokenRequest);

    CompletableFuture<TokenResponse> request(WalletTokenRequest walletTokenRequest);

}
