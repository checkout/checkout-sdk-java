package com.checkout.forex;

import java.util.concurrent.CompletableFuture;

public interface ForexClient {

    CompletableFuture<QuoteResponse> requestQuote(QuoteRequest quoteRequest);

    CompletableFuture<RatesQueryResponse> getRates(RatesQueryFilter ratesQuery);
}
