package com.checkout.tokens.beta;

import java.util.concurrent.CompletableFuture;

public interface TokensClient {

    CompletableFuture<CardTokenResponse> requestAsync(CardTokenRequest cardTokenRequest);

    CompletableFuture<TokenResponse> requestAsync(WalletTokenRequest walletTokenRequest);

}
