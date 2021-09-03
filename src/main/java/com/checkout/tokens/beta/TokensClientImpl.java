package com.checkout.tokens.beta;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.PublicKeyCredentials;
import com.checkout.tokens.beta.request.CardTokenRequest;
import com.checkout.tokens.beta.request.WalletTokenRequest;
import com.checkout.tokens.beta.response.CardTokenResponse;
import com.checkout.tokens.beta.response.TokenResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public final class TokensClientImpl extends AbstractClient implements TokensClient {

    private static final String TOKENS_PATH = "tokens";

    public TokensClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, PublicKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<CardTokenResponse> requestAsync(final CardTokenRequest cardTokenRequest) {
        validateParams("cardTokenRequest", cardTokenRequest);
        return apiClient.postAsync(TOKENS_PATH, apiCredentials, CardTokenResponse.class, cardTokenRequest, null);
    }

    @Override
    public CompletableFuture<TokenResponse> requestAsync(final WalletTokenRequest walletTokenRequest) {
        validateParams("walletTokenRequest", walletTokenRequest);
        return apiClient.postAsync(TOKENS_PATH, apiCredentials, TokenResponse.class, walletTokenRequest, null);
    }

}
