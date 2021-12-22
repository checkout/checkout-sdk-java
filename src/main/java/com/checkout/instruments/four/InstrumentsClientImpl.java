package com.checkout.instruments.four;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.four.create.CreateInstrumentRequest;
import com.checkout.instruments.four.create.CreateInstrumentResponse;
import com.checkout.instruments.four.get.BankAccountFieldQuery;
import com.checkout.instruments.four.get.BankAccountFieldResponse;
import com.checkout.instruments.four.get.GetInstrumentResponse;
import com.checkout.instruments.four.update.UpdateInstrumentRequest;
import com.checkout.instruments.four.update.UpdateInstrumentResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

public class InstrumentsClientImpl extends AbstractClient implements InstrumentsClient {

    private static final String INSTRUMENTS_PATH = "instruments";
    private static final String VALIDATION_PATH = "validation/bank-accounts";

    private static final Type CREATE_TYPE = TypeToken.get(CreateInstrumentResponse.class).getType();
    private static final Type UPDATE_TYPE = TypeToken.get(UpdateInstrumentResponse.class).getType();
    private static final Type GET_TYPE = TypeToken.get(GetInstrumentResponse.class).getType();

    public InstrumentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public <T extends CreateInstrumentResponse> CompletableFuture<T> create(final CreateInstrumentRequest createInstrumentRequest) {
        CheckoutUtils.validateParams("createInstrumentRequest", createInstrumentRequest);
        return apiClient.postAsync(INSTRUMENTS_PATH, sdkAuthorization(), CREATE_TYPE, createInstrumentRequest, null);
    }

    @Override
    public CompletableFuture<GetInstrumentResponse> get(final String instrumentId) {
        CheckoutUtils.validateParams("instrumentId", instrumentId);
        return apiClient.getAsync(buildPath(INSTRUMENTS_PATH, instrumentId), sdkAuthorization(), GET_TYPE);
    }

    @Override
    public <T extends UpdateInstrumentResponse> CompletableFuture<T> update(final String instrumentId, final UpdateInstrumentRequest updateInstrumentRequest) {
        CheckoutUtils.validateParams("instrumentId", instrumentId, "updateInstrumentRequest", updateInstrumentRequest);
        return apiClient.patchAsync(buildPath(INSTRUMENTS_PATH, instrumentId), sdkAuthorization(), UPDATE_TYPE, updateInstrumentRequest, null);
    }

    @Override
    public CompletableFuture<Void> delete(final String instrumentId) {
        CheckoutUtils.validateParams("instrumentId", instrumentId);
        return apiClient.deleteAsync(buildPath(INSTRUMENTS_PATH, instrumentId), sdkAuthorization());
    }

    @Override
    public CompletableFuture<BankAccountFieldResponse> getBankAccountFieldFormatting(final CountryCode country, final Currency currency, final BankAccountFieldQuery query) {
        CheckoutUtils.validateParams("country", country, "currency", currency, "query", query);
        return apiClient.queryAsync(buildPath(VALIDATION_PATH, country.name(), currency.name()), sdkAuthorization(SdkAuthorizationType.OAUTH), query, BankAccountFieldResponse.class);
    }

}
