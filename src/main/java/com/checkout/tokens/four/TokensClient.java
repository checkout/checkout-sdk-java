package com.checkout.tokens.four;

import com.checkout.tokens.four.request.CardTokenRequest;
import com.checkout.tokens.four.request.WalletTokenRequest;
import com.checkout.tokens.four.response.CardTokenResponse;
import com.checkout.tokens.four.response.TokenResponse;

import java.util.concurrent.CompletableFuture;

public interface TokensClient {

    CompletableFuture<CardTokenResponse> requestAsync(CardTokenRequest cardTokenRequest);

    CompletableFuture<TokenResponse> requestAsync(WalletTokenRequest walletTokenRequest);

}
