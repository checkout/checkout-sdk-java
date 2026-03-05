package com.checkout.networkTokens;

import com.checkout.EmptyResponse;
import com.checkout.networkTokens.requests.DeleteNetworkTokenRequest;
import com.checkout.networkTokens.requests.ProvisionNetworkTokenRequest;
import com.checkout.networkTokens.requests.RequestCryptogramRequest;
import com.checkout.networkTokens.responses.CryptogramResponse;
import com.checkout.networkTokens.responses.NetworkTokenResponse;

import java.util.concurrent.CompletableFuture;

public interface NetworkTokensClient {

    /**
     * Provision a Network Token
     * <b>Beta</b>
     * Provisions a network token synchronously. If the merchant stores their cards with Checkout.com, 
     * then source ID can be used to request a network token for the given card. If the merchant does 
     * not store their cards with Checkout.com, then card details have to be provided.
     */
    CompletableFuture<NetworkTokenResponse> provisionNetworkToken(ProvisionNetworkTokenRequest provisionNetworkTokenRequest);

    /**
     * Get Network Token
     * <b>Beta</b>
     * Given network token ID, this endpoint returns network token details: DPAN, expiry date, state, 
     * TRID and also card details like last four and expiry date.
     */
    CompletableFuture<NetworkTokenResponse> getNetworkToken(String networkTokenId);

    /**
     * Request a cryptogram
     * <b>Beta</b>
     * Using network token ID as an input, this endpoint returns token cryptogram.
     */
    CompletableFuture<CryptogramResponse> requestCryptogram(String networkTokenId, RequestCryptogramRequest requestCryptogramRequest);

    /**
     * Permanently deletes a network token
     * <b>Beta</b>
     * This endpoint is for permanently deleting a network token. A network token should be deleted 
     * when a payment instrument it is associated with is removed from file or if the security of the 
     * token has been compromised.
     */
    CompletableFuture<EmptyResponse> deleteNetworkToken(String networkTokenId, DeleteNetworkTokenRequest deleteNetworkTokenRequest);

    // Synchronous methods
    NetworkTokenResponse provisionNetworkTokenSync(ProvisionNetworkTokenRequest provisionNetworkTokenRequest);

    NetworkTokenResponse getNetworkTokenSync(String networkTokenId);

    CryptogramResponse requestCryptogramSync(String networkTokenId, RequestCryptogramRequest requestCryptogramRequest);

    EmptyResponse deleteNetworkTokenSync(String networkTokenId, DeleteNetworkTokenRequest deleteNetworkTokenRequest);

}