package com.checkout.networktokens;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.networktokens.requests.DeleteNetworkTokenRequest;
import com.checkout.networktokens.requests.ProvisionNetworkTokenRequest;
import com.checkout.networktokens.requests.RequestCryptogramRequest;
import com.checkout.networktokens.responses.CryptogramResponse;
import com.checkout.networktokens.responses.NetworkTokenResponse;

import java.util.concurrent.CompletableFuture;

public class NetworkTokensClientImpl extends AbstractClient implements NetworkTokensClient {

    private static final String NETWORK_TOKENS_PATH = "network-tokens";
    private static final String CRYPTOGRAMS_PATH = "cryptograms";
    private static final String DELETE_PATH = "delete";

    public NetworkTokensClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
    }

    @Override
    public CompletableFuture<NetworkTokenResponse> provisionNetworkToken(final ProvisionNetworkTokenRequest provisionNetworkTokenRequest) {
        CheckoutUtils.validateParams("provisionNetworkTokenRequest", provisionNetworkTokenRequest);
        return apiClient.postAsync(NETWORK_TOKENS_PATH, sdkAuthorization(), NetworkTokenResponse.class, provisionNetworkTokenRequest, null);
    }

    @Override
    public CompletableFuture<NetworkTokenResponse> getNetworkToken(final String networkTokenId) {
        CheckoutUtils.validateParams("networkTokenId", networkTokenId);
        return apiClient.getAsync(buildPath(NETWORK_TOKENS_PATH, networkTokenId), sdkAuthorization(), NetworkTokenResponse.class);
    }

    @Override
    public CompletableFuture<CryptogramResponse> requestCryptogram(final String networkTokenId, final RequestCryptogramRequest requestCryptogramRequest) {
        CheckoutUtils.validateParams("networkTokenId", networkTokenId, "requestCryptogramRequest", requestCryptogramRequest);
        return apiClient.postAsync(buildPath(NETWORK_TOKENS_PATH, networkTokenId, CRYPTOGRAMS_PATH), sdkAuthorization(), CryptogramResponse.class, requestCryptogramRequest, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> deleteNetworkToken(final String networkTokenId, final DeleteNetworkTokenRequest deleteNetworkTokenRequest) {
        CheckoutUtils.validateParams("networkTokenId", networkTokenId, "deleteNetworkTokenRequest", deleteNetworkTokenRequest);
        return apiClient.patchAsync(buildPath(NETWORK_TOKENS_PATH, networkTokenId, DELETE_PATH), sdkAuthorization(), EmptyResponse.class, deleteNetworkTokenRequest, null);
    }

    // Synchronous methods
    @Override
    public NetworkTokenResponse provisionNetworkTokenSync(final ProvisionNetworkTokenRequest provisionNetworkTokenRequest) {
        CheckoutUtils.validateParams("provisionNetworkTokenRequest", provisionNetworkTokenRequest);
        return apiClient.post(NETWORK_TOKENS_PATH, sdkAuthorization(), NetworkTokenResponse.class, provisionNetworkTokenRequest, null);
    }

    @Override
    public NetworkTokenResponse getNetworkTokenSync(final String networkTokenId) {
        CheckoutUtils.validateParams("networkTokenId", networkTokenId);
        return apiClient.get(buildPath(NETWORK_TOKENS_PATH, networkTokenId), sdkAuthorization(), NetworkTokenResponse.class);
    }

    @Override
    public CryptogramResponse requestCryptogramSync(final String networkTokenId, final RequestCryptogramRequest requestCryptogramRequest) {
        CheckoutUtils.validateParams("networkTokenId", networkTokenId, "requestCryptogramRequest", requestCryptogramRequest);
        return apiClient.post(buildPath(NETWORK_TOKENS_PATH, networkTokenId, CRYPTOGRAMS_PATH), sdkAuthorization(), CryptogramResponse.class, requestCryptogramRequest, null);
    }

    @Override
    public EmptyResponse deleteNetworkTokenSync(final String networkTokenId, final DeleteNetworkTokenRequest deleteNetworkTokenRequest) {
        CheckoutUtils.validateParams("networkTokenId", networkTokenId, "deleteNetworkTokenRequest", deleteNetworkTokenRequest);
        return apiClient.patch(buildPath(NETWORK_TOKENS_PATH, networkTokenId, DELETE_PATH), sdkAuthorization(), EmptyResponse.class, deleteNetworkTokenRequest, null);
    }

}