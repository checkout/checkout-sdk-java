package com.checkout.instruments;

import java.util.concurrent.CompletableFuture;

public interface InstrumentsClient {

    CompletableFuture<CreateInstrumentResponse> createInstrument(CreateInstrumentRequest createInstrumentRequest);

    CompletableFuture<InstrumentDetailsResponse> getInstrument(String instrumentId);

    CompletableFuture<UpdateInstrumentResponse> updateInstrument(String instrumentId, UpdateInstrumentRequest updateInstrumentRequest);

    /**
     * @param instrumentId - matching a pattern ^(src)_(\w{26})$
     */
    CompletableFuture<Void> deleteInstrument(String instrumentId);
}
