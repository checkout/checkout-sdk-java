package com.checkout.forex.four;

import java.util.concurrent.CompletableFuture;

public interface ForexClient {

    CompletableFuture<QuoteResponse> requestQuote(QuoteRequest quoteRequest);
}
