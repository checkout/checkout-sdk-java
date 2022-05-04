package com.checkout.tokens;

import java.util.concurrent.CompletableFuture;

public interface TokensClient {

    CompletableFuture<CardTokenResponse> requestCardToken(CardTokenRequest cardTokenRequest);

    CompletableFuture<TokenResponse> requestWalletToken(WalletTokenRequest walletTokenRequest);

}
