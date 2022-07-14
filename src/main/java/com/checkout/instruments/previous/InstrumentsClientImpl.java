package com.checkout.instruments.previous;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class InstrumentsClientImpl extends AbstractClient implements InstrumentsClient {

    private static final String INSTRUMENTS_PATH = "instruments";
    private static final String INSTRUMENT_ID = "instrumentId";

    public InstrumentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<CreateInstrumentResponse> create(final CreateInstrumentRequest createInstrumentRequest) {
        validateParams("createInstrumentRequest", createInstrumentRequest);
        return apiClient.postAsync(INSTRUMENTS_PATH, sdkAuthorization(), CreateInstrumentResponse.class, createInstrumentRequest, null);
    }

    @Override
    public CompletableFuture<InstrumentDetailsResponse> get(final String instrumentId) {
        validateParams(INSTRUMENT_ID, instrumentId);
        return apiClient.getAsync(buildPath(INSTRUMENTS_PATH, instrumentId), sdkAuthorization(), InstrumentDetailsResponse.class);
    }

    @Override
    public CompletableFuture<UpdateInstrumentResponse> update(final String instrumentId, final UpdateInstrumentRequest updateInstrumentRequest) {
        validateParams(INSTRUMENT_ID, instrumentId, "updateInstrumentRequest", updateInstrumentRequest);
        return apiClient.patchAsync(buildPath(INSTRUMENTS_PATH, instrumentId), sdkAuthorization(), UpdateInstrumentResponse.class, updateInstrumentRequest, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> delete(final String instrumentId) {
        validateParams(INSTRUMENT_ID, instrumentId);
        return apiClient.deleteAsync(buildPath(INSTRUMENTS_PATH, instrumentId), sdkAuthorization());
    }

}
