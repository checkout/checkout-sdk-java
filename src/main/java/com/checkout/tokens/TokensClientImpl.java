package com.checkout.tokens;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.PublicKeyCredentials;
import com.checkout.beta.AbstractClient;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.requiresNonNull;

public class TokensClientImpl extends AbstractClient implements TokensClient {

    private static final String TOKENS_PATH = "tokens";

    public TokensClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, PublicKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<CardTokenResponse> requestAsync(final CardTokenRequest cardTokenRequest) {
        requiresNonNull("cardTokenRequest", cardTokenRequest);
        return apiClient.postAsync(TOKENS_PATH, apiCredentials, CardTokenResponse.class, cardTokenRequest, null);
    }

    @Override
    public CompletableFuture<TokenResponse> requestAsync(final WalletTokenRequest walletTokenRequest) {
        requiresNonNull("walletTokenRequest", walletTokenRequest);
        return apiClient.postAsync(TOKENS_PATH, apiCredentials, TokenResponse.class, walletTokenRequest, null);
    }

}
