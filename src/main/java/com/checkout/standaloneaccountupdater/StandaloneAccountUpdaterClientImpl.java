package com.checkout.standaloneaccountupdater;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.standaloneaccountupdater.requests.GetUpdatedCardCredentialsRequest;
import com.checkout.standaloneaccountupdater.responses.GetUpdatedCardCredentialsResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the standalone account updater client.
 */
public class StandaloneAccountUpdaterClientImpl extends AbstractClient implements StandaloneAccountUpdaterClient {

    private static final String ACCOUNT_UPDATER_PATH = "account-updater";
    private static final String CARDS_PATH = "cards";

    public StandaloneAccountUpdaterClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<GetUpdatedCardCredentialsResponse> getUpdatedCardCredentials(
            final GetUpdatedCardCredentialsRequest getUpdatedCardCredentialsRequest) {
        validateGetUpdatedCardCredentialsRequest(getUpdatedCardCredentialsRequest);
        return apiClient.postAsync(buildPath(ACCOUNT_UPDATER_PATH, CARDS_PATH), sdkAuthorization(), 
                GetUpdatedCardCredentialsResponse.class, getUpdatedCardCredentialsRequest, null);
    }

    // Synchronous methods
    @Override
    public GetUpdatedCardCredentialsResponse getUpdatedCardCredentialsSync(
            final GetUpdatedCardCredentialsRequest getUpdatedCardCredentialsRequest) {
        validateGetUpdatedCardCredentialsRequest(getUpdatedCardCredentialsRequest);
        return apiClient.post(buildPath(ACCOUNT_UPDATER_PATH, CARDS_PATH), sdkAuthorization(), 
                GetUpdatedCardCredentialsResponse.class, getUpdatedCardCredentialsRequest, null);
    }

    // Common methods
    private void validateGetUpdatedCardCredentialsRequest(final GetUpdatedCardCredentialsRequest request) {
        CheckoutUtils.validateParams("getUpdatedCardCredentialsRequest", request);
        if (request.getSourceOptions() == null) {
            throw new IllegalArgumentException("sourceOptions cannot be null");
        }
        if (request.getSourceOptions().getCard() == null && request.getSourceOptions().getInstrument() == null) {
            throw new IllegalArgumentException("Either card or instrument must be provided in sourceOptions");
        }
        if (request.getSourceOptions().getCard() != null && request.getSourceOptions().getInstrument() != null) {
            throw new IllegalArgumentException("You must provide either card or instrument, but not both");
        }
        if (request.getSourceOptions().getCard() != null) {
            validateCardDetails(request.getSourceOptions().getCard());
        }
        if (request.getSourceOptions().getInstrument() != null) {
            validateInstrumentReference(request.getSourceOptions().getInstrument());
        }
    }

    private void validateCardDetails(final com.checkout.standaloneaccountupdater.entities.CardDetails cardDetails) {
        if (cardDetails.getNumber() == null || cardDetails.getNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }
        if (!cardDetails.getNumber().matches("^[0-9]+$")) {
            throw new IllegalArgumentException("Card number must contain only digits");
        }
        if (cardDetails.getExpiryMonth() == null) {
            throw new IllegalArgumentException("Card expiry month cannot be null");
        }
        if (cardDetails.getExpiryMonth() < 1 || cardDetails.getExpiryMonth() > 12) {
            throw new IllegalArgumentException("Card expiry month must be between 1 and 12");
        }
        if (cardDetails.getExpiryYear() == null) {
            throw new IllegalArgumentException("Card expiry year cannot be null");
        }
    }

    private void validateInstrumentReference(final com.checkout.standaloneaccountupdater.entities.InstrumentReference instrumentReference) {
        if (instrumentReference.getId() == null || instrumentReference.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Instrument ID cannot be null or empty");
        }
    }
}