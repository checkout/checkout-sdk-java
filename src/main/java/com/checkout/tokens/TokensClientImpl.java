package com.checkout.tokens;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.PublicKeyCredentials;
import com.checkout.beta.AbstractClient;

import java.util.concurrent.CompletableFuture;

public class TokensClientImpl extends AbstractClient implements TokensClient {

    public TokensClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, PublicKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<CardTokenResponse> requestAsync(final CardTokenRequest cardTokenRequest) {
        if (cardTokenRequest == null) {
            throw new IllegalArgumentException("cardTokenRequest must not be null");
        }
        return apiClient.postAsync("tokens", apiCredentials, CardTokenResponse.class, cardTokenRequest, null);
    }

    @Override
    public CompletableFuture<TokenResponse> requestAsync(final WalletTokenRequest walletTokenRequest) {
        if (walletTokenRequest == null) {
            throw new IllegalArgumentException("walletTokenRequest must not be null");
        }

        return apiClient.postAsync("tokens", apiCredentials, TokenResponse.class, walletTokenRequest, null);
    }
}
