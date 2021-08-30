package com.checkout.tokens;

import java.util.concurrent.CompletableFuture;

public interface TokensClient {

    CompletableFuture<CardTokenResponse> requestAsync(CardTokenRequest cardTokenRequest);

    CompletableFuture<TokenResponse> requestAsync(WalletTokenRequest walletTokenRequest);

}
