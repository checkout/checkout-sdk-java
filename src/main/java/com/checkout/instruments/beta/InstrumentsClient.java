package com.checkout.instruments.beta;

import com.checkout.instruments.beta.create.CreateInstrumentRequest;
import com.checkout.instruments.beta.create.CreateInstrumentResponse;
import com.checkout.instruments.beta.get.GetInstrumentResponse;
import com.checkout.instruments.beta.update.UpdateInstrumentRequest;
import com.checkout.instruments.beta.update.UpdateInstrumentResponse;

import java.util.concurrent.CompletableFuture;

public interface InstrumentsClient {

    <T extends CreateInstrumentResponse> CompletableFuture<T> create(CreateInstrumentRequest createInstrumentRequest);

    CompletableFuture<GetInstrumentResponse> get(String instrumentId);

    <T extends UpdateInstrumentResponse> CompletableFuture<T> update(String instrumentId, UpdateInstrumentRequest updateInstrumentRequest);

    CompletableFuture<Void> delete(String instrumentId);
}
