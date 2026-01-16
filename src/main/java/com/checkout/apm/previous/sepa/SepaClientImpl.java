package com.checkout.apm.previous.sepa;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class SepaClientImpl extends AbstractClient implements SepaClient {

    private static final String APMS = "apms";
    private static final String SEPA_MANDATES = "sepa/mandates";
    private static final String PPRO = "ppro";
    private static final String CANCEL = "cancel";
    private static final String MANDATE_ID = "mandateId";

    public SepaClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<MandateResponse> getMandate(final String mandateId) {
        validateMandateId(mandateId);
        return apiClient.getAsync(buildPath(SEPA_MANDATES, mandateId), sdkAuthorization(), MandateResponse.class);
    }

    @Override
    public CompletableFuture<SepaResource> cancelMandate(final String mandateId) {
        validateMandateId(mandateId);
        return apiClient.postAsync(buildPath(SEPA_MANDATES, mandateId, CANCEL), sdkAuthorization(), SepaResource.class, null, null);
    }

    @Override
    public CompletableFuture<MandateResponse> getMandateViaPPRO(final String mandateId) {
        validateMandateId(mandateId);
        return apiClient.getAsync(buildPath(APMS, PPRO, SEPA_MANDATES, mandateId), sdkAuthorization(), MandateResponse.class);
    }

    @Override
    public CompletableFuture<SepaResource> cancelMandateViaPPRO(final String mandateId) {
        validateMandateId(mandateId);
        return apiClient.postAsync(buildPath(APMS, PPRO, SEPA_MANDATES, mandateId, CANCEL), sdkAuthorization(), SepaResource.class, null, null);
    }

    // Synchronous methods
    @Override
    public MandateResponse getMandateSync(final String mandateId) {
        validateMandateId(mandateId);
        return apiClient.get(buildPath(SEPA_MANDATES, mandateId), sdkAuthorization(), MandateResponse.class);
    }

    @Override
    public SepaResource cancelMandateSync(final String mandateId) {
        validateMandateId(mandateId);
        return apiClient.post(buildPath(SEPA_MANDATES, mandateId, CANCEL), sdkAuthorization(), SepaResource.class, null, null);
    }

    @Override
    public MandateResponse getMandateViaPPROSync(final String mandateId) {
        validateMandateId(mandateId);
        return apiClient.get(buildPath(APMS, PPRO, SEPA_MANDATES, mandateId), sdkAuthorization(), MandateResponse.class);
    }

    @Override
    public SepaResource cancelMandateViaPPROSync(final String mandateId) {
        validateMandateId(mandateId);
        return apiClient.post(buildPath(APMS, PPRO, SEPA_MANDATES, mandateId, CANCEL), sdkAuthorization(), SepaResource.class, null, null);
    }

    // Common methods
    protected void validateMandateId(final String mandateId) {
        validateParams(MANDATE_ID, mandateId);
    }

}
