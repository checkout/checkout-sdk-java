package com.checkout.sources.previous;

import java.util.concurrent.CompletableFuture;

public interface SourcesClient {
    CompletableFuture<SepaSourceResponse> createSepaSource(SepaSourceRequest sepaSourceRequest);
}
