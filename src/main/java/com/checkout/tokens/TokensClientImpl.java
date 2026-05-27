package com.checkout.tokens;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class TokensClientImpl extends AbstractClient implements TokensClient {

    private static final String TOKENS_PATH = "tokens";
    private static final String METADATA_PATH = "metadata";

    public TokensClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.PUBLIC_KEY);
    }

    @Override
    public CompletableFuture<CardTokenResponse> requestCardToken(final CardTokenRequest cardTokenRequest) {
        validateCardTokenRequest(cardTokenRequest);
        return apiClient.postAsync(TOKENS_PATH, sdkAuthorization(), CardTokenResponse.class, cardTokenRequest, null);
    }

    @Override
    public CompletableFuture<TokenResponse> requestWalletToken(final WalletTokenRequest walletTokenRequest) {
        validateWalletTokenRequest(walletTokenRequest);
        return apiClient.postAsync(TOKENS_PATH, sdkAuthorization(), TokenResponse.class, walletTokenRequest, null);
    }

    @Override
    public CompletableFuture<TokenMetadataResponse> getTokenMetadata(final String tokenId) {
        validateParams("tokenId", tokenId);
        return apiClient.getAsync(buildPath(TOKENS_PATH, tokenId, METADATA_PATH),
                sdkAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH), TokenMetadataResponse.class);
    }

    // Synchronous methods
    @Override
    public CardTokenResponse requestCardTokenSync(final CardTokenRequest cardTokenRequest) {
        validateCardTokenRequest(cardTokenRequest);
        return apiClient.post(TOKENS_PATH, sdkAuthorization(), CardTokenResponse.class, cardTokenRequest, null);
    }

    @Override
    public TokenResponse requestWalletTokenSync(final WalletTokenRequest walletTokenRequest) {
        validateWalletTokenRequest(walletTokenRequest);
        return apiClient.post(TOKENS_PATH, sdkAuthorization(), TokenResponse.class, walletTokenRequest, null);
    }

    @Override
    public TokenMetadataResponse getTokenMetadataSync(final String tokenId) {
        validateParams("tokenId", tokenId);
        return apiClient.get(buildPath(TOKENS_PATH, tokenId, METADATA_PATH),
                sdkAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH), TokenMetadataResponse.class);
    }

    // Common methods
    protected void validateCardTokenRequest(final CardTokenRequest cardTokenRequest) {
        validateParams("cardTokenRequest", cardTokenRequest);
    }

    protected void validateWalletTokenRequest(final WalletTokenRequest walletTokenRequest) {
        validateParams("walletTokenRequest", walletTokenRequest);
    }

}
