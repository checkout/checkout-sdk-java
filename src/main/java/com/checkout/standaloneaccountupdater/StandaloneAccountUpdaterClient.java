package com.checkout.standaloneaccountupdater;

import com.checkout.standaloneaccountupdater.requests.GetUpdatedCardCredentialsRequest;
import com.checkout.standaloneaccountupdater.responses.GetUpdatedCardCredentialsResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client interface for standalone account updater operations.
 */
public interface StandaloneAccountUpdaterClient {

    /**
     * Get updated card credentials.
     * Retrieve updated card credentials. The following card schemes are supported: Mastercard, Visa, American Express.
     *
     * @param getUpdatedCardCredentialsRequest The get updated card credentials request
     * @return CompletableFuture containing the get updated card credentials response
     */
    CompletableFuture<GetUpdatedCardCredentialsResponse> getUpdatedCardCredentials(GetUpdatedCardCredentialsRequest getUpdatedCardCredentialsRequest);

    // Synchronous methods
    /**
     * Get updated card credentials (synchronous).
     * Retrieve updated card credentials. The following card schemes are supported: Mastercard, Visa, American Express.
     *
     * @param getUpdatedCardCredentialsRequest The get updated card credentials request
     * @return The get updated card credentials response
     */
    GetUpdatedCardCredentialsResponse getUpdatedCardCredentialsSync(GetUpdatedCardCredentialsRequest getUpdatedCardCredentialsRequest);
}