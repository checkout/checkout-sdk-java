package com.checkout.forex;

import java.util.concurrent.CompletableFuture;

public interface ForexClient {

    CompletableFuture<QuoteResponse> requestQuote(QuoteRequest quoteRequest);

    CompletableFuture<RatesQueryResponse> getRates(RatesQueryFilter ratesQuery);

    // Synchronous methods
    QuoteResponse requestQuoteSync(QuoteRequest quoteRequest);

    RatesQueryResponse getRatesSync(RatesQueryFilter ratesQuery);
}
