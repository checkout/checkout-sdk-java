package com.checkout.sources;

import java.util.concurrent.CompletableFuture;

public interface SourcesClient {
    CompletableFuture<SepaSourceResponse> createSepaSource(SepaSourceRequest sepaSourceRequest);
}
