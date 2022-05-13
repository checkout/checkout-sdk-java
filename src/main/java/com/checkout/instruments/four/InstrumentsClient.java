package com.checkout.instruments.four;

import com.checkout.EmptyResponse;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.four.create.CreateInstrumentRequest;
import com.checkout.instruments.four.create.CreateInstrumentResponse;
import com.checkout.instruments.four.get.BankAccountFieldQuery;
import com.checkout.instruments.four.get.BankAccountFieldResponse;
import com.checkout.instruments.four.get.GetInstrumentResponse;
import com.checkout.instruments.four.update.UpdateInstrumentRequest;
import com.checkout.instruments.four.update.UpdateInstrumentResponse;

import java.util.concurrent.CompletableFuture;

public interface InstrumentsClient {

    <T extends CreateInstrumentResponse> CompletableFuture<T> create(CreateInstrumentRequest createInstrumentRequest);

    CompletableFuture<GetInstrumentResponse> get(String instrumentId);

    <T extends UpdateInstrumentResponse> CompletableFuture<T> update(String instrumentId, UpdateInstrumentRequest updateInstrumentRequest);

    CompletableFuture<EmptyResponse> delete(String instrumentId);

    CompletableFuture<BankAccountFieldResponse> getBankAccountFieldFormatting(CountryCode country, Currency currency, BankAccountFieldQuery query);
}
