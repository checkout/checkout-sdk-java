package com.checkout.metadata;

import com.checkout.metadata.card.CardMetadataRequest;
import com.checkout.metadata.card.CardMetadataResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client for the Card Metadata API (POST /metadata/card).
 * Returns a single metadata record for the card specified by PAN, BIN, token, or instrument ID.
 * <p>
 * Beta endpoint — requires OAuth scope {@code vault:card-metadata} or a secret API key.
 * </p>
 */
public interface MetadataClient {

    /**
     * Retrieves card metadata asynchronously for the specified card source.
     *
     * @param cardMetadataRequest the request identifying the card source (PAN, BIN, token, or instrument ID)
     *                            and the desired response format
     * @return a {@link CompletableFuture} containing the card metadata response
     */
    CompletableFuture<CardMetadataResponse> requestCardMetadata(CardMetadataRequest cardMetadataRequest);

    /**
     * Retrieves card metadata synchronously for the specified card source.
     *
     * @param cardMetadataRequest the request identifying the card source (PAN, BIN, token, or instrument ID)
     *                            and the desired response format
     * @return the card metadata response
     */
    CardMetadataResponse requestCardMetadataSync(CardMetadataRequest cardMetadataRequest);
}
