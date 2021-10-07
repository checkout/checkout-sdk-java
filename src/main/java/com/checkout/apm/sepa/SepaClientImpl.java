package com.checkout.apm.sepa;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.Resource;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class SepaClientImpl extends AbstractClient implements SepaClient {

    private static final String SEPA_MANDATES = "sepa/mandates";
    private static final String PPRO = "/ppro";
    private static final String CANCEL = "cancel";
    private static final String MANDATE_ID = "mandateId";

    public SepaClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<MandateResponse> getMandate(final String mandateId) {
        validateParams(MANDATE_ID, mandateId);
        return apiClient.getAsync(buildPath(SEPA_MANDATES, mandateId), sdkAuthorization(), MandateResponse.class);
    }

    @Override
    public CompletableFuture<Resource> cancelMandate(final String mandateId) {
        validateParams(MANDATE_ID, mandateId);
        return apiClient.postAsync(buildPath(SEPA_MANDATES, mandateId, CANCEL), sdkAuthorization(), Resource.class, null, null);
    }

    @Override
    public CompletableFuture<MandateResponse> getMandateViaPPRO(final String mandateId) {
        validateParams(MANDATE_ID, mandateId);
        return apiClient.getAsync(buildPath(PPRO, SEPA_MANDATES, mandateId), sdkAuthorization(), MandateResponse.class);
    }

    @Override
    public CompletableFuture<Resource> cancelMandateViaPPRO(final String mandateId) {
        validateParams(MANDATE_ID, mandateId);
        return apiClient.postAsync(buildPath(PPRO, SEPA_MANDATES, mandateId, CANCEL), sdkAuthorization(), Resource.class, null, null);
    }

}
