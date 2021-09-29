package com.checkout.tokens;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class TokensClientImpl extends AbstractClient implements TokensClient {

    private static final String TOKENS_PATH = "tokens";

    public TokensClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.PUBLIC_KEY);
    }

    @Override
    public CompletableFuture<CardTokenResponse> requestAsync(final CardTokenRequest cardTokenRequest) {
        validateParams("cardTokenRequest", cardTokenRequest);
        return apiClient.postAsync(TOKENS_PATH, sdkAuthorization(), CardTokenResponse.class, cardTokenRequest, null);
    }

    @Override
    public CompletableFuture<TokenResponse> requestAsync(final WalletTokenRequest walletTokenRequest) {
        validateParams("walletTokenRequest", walletTokenRequest);
        return apiClient.postAsync(TOKENS_PATH, sdkAuthorization(), TokenResponse.class, walletTokenRequest, null);
    }

}
