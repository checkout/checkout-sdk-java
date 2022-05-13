package com.checkout.instruments;

import com.checkout.EmptyResponse;

import java.util.concurrent.CompletableFuture;

public interface InstrumentsClient {

    CompletableFuture<CreateInstrumentResponse> create(CreateInstrumentRequest createInstrumentRequest);

    CompletableFuture<InstrumentDetailsResponse> get(String instrumentId);

    CompletableFuture<UpdateInstrumentResponse> update(String instrumentId, UpdateInstrumentRequest updateInstrumentRequest);

    /**
     * @param instrumentId - matching a pattern ^(src)_(\w{26})$
     */
    CompletableFuture<EmptyResponse> delete(String instrumentId);
}
