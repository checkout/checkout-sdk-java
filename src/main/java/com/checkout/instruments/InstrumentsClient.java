package com.checkout.instruments;

import java.util.concurrent.CompletableFuture;

public interface InstrumentsClient {

    CompletableFuture<CreateInstrumentResponse> createInstrument(CreateInstrumentRequest createInstrumentRequest);
}
