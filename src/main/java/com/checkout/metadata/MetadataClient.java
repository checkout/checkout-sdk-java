package com.checkout.metadata;

import com.checkout.metadata.card.CardMetadataRequest;
import com.checkout.metadata.card.CardMetadataResponse;

import java.util.concurrent.CompletableFuture;

public interface MetadataClient {

    CompletableFuture<CardMetadataResponse> requestCardMetadata(CardMetadataRequest cardMetadataRequest);

    // Synchronous methods
    CardMetadataResponse requestCardMetadataSync(CardMetadataRequest cardMetadataRequest);
}
