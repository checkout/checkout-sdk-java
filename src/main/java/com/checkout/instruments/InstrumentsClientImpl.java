package com.checkout.instruments;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class InstrumentsClientImpl extends AbstractClient implements InstrumentsClient {

    private static final String INSTRUMENTS = "instruments";

    public InstrumentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<CreateInstrumentResponse> createInstrument(final CreateInstrumentRequest createInstrumentRequest) {
        validateParams("createInstrumentRequest", createInstrumentRequest);
        return apiClient.postAsync(INSTRUMENTS, sdkAuthorization(), CreateInstrumentResponse.class, createInstrumentRequest, null);
    }

    @Override
    public CompletableFuture<InstrumentDetailsResponse> getInstrument(final String instrumentId) {
        validateParams("instrumentId", instrumentId);
        return apiClient.getAsync(buildPath(INSTRUMENTS, instrumentId), sdkAuthorization(), InstrumentDetailsResponse.class);
    }

    @Override
    public CompletableFuture<UpdateInstrumentResponse> updateInstrument(final String instrumentId, final UpdateInstrumentRequest updateInstrumentRequest) {
        validateParams("instrumentId", instrumentId, "updateInstrumentRequest", updateInstrumentRequest);
        return apiClient.patchAsync(buildPath(INSTRUMENTS, instrumentId), sdkAuthorization(), UpdateInstrumentResponse.class, updateInstrumentRequest, null);
    }

    @Override
    public CompletableFuture<Void> deleteInstrument(final String instrumentId) {
        validateParams("instrumentId", instrumentId);
        return apiClient.deleteAsync(buildPath(INSTRUMENTS, instrumentId), sdkAuthorization());
    }

}
