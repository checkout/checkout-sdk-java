package com.checkout.tokens.beta;

import com.checkout.tokens.beta.request.CardTokenRequest;
import com.checkout.tokens.beta.request.WalletTokenRequest;
import com.checkout.tokens.beta.response.CardTokenResponse;
import com.checkout.tokens.beta.response.TokenResponse;

import java.util.concurrent.CompletableFuture;

public interface TokensClient {

    CompletableFuture<CardTokenResponse> requestAsync(CardTokenRequest cardTokenRequest);

    CompletableFuture<TokenResponse> requestAsync(WalletTokenRequest walletTokenRequest);

}
