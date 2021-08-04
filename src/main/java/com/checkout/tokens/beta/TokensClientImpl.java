package com.checkout.tokens.beta;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.PublicKeyCredentials;
import com.checkout.beta.AbstractClient;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.requiresNonNull;

public final class TokensClientImpl extends AbstractClient implements TokensClient {

    public TokensClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, PublicKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<CardTokenResponse> requestAsync(final CardTokenRequest cardTokenRequest) {
        requiresNonNull("cardTokenRequest", cardTokenRequest);
        return apiClient.postAsync("tokens", apiCredentials, CardTokenResponse.class, cardTokenRequest, null);
    }

    @Override
    public CompletableFuture<TokenResponse> requestAsync(final WalletTokenRequest walletTokenRequest) {
        requiresNonNull("walletTokenRequest", walletTokenRequest);
        return apiClient.postAsync("tokens", apiCredentials, TokenResponse.class, walletTokenRequest, null);
    }

}
