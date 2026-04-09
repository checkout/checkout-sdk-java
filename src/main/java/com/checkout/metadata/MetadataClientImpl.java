package com.checkout.metadata;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.metadata.card.CardMetadataRequest;
import com.checkout.metadata.card.CardMetadataResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Default implementation of {@link MetadataClient}. Posts to POST /metadata/card
 * using secret key or OAuth ({@code vault:card-metadata}) authorization.
 */
public class MetadataClientImpl extends AbstractClient implements MetadataClient {

    private static final String METADATA_CARD_PATH = "metadata/card";

    /**
     * Constructs a new {@code MetadataClientImpl}.
     *
     * @param apiClient     the HTTP client used to perform API calls
     * @param configuration the SDK configuration (credentials, environment, etc.)
     */
    public MetadataClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<CardMetadataResponse> requestCardMetadata(final CardMetadataRequest cardMetadataRequest) {
        validateCardMetadataRequest(cardMetadataRequest);
        return apiClient.postAsync(METADATA_CARD_PATH, sdkAuthorization(), CardMetadataResponse.class, cardMetadataRequest, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardMetadataResponse requestCardMetadataSync(final CardMetadataRequest cardMetadataRequest) {
        validateCardMetadataRequest(cardMetadataRequest);
        return apiClient.post(METADATA_CARD_PATH, sdkAuthorization(), CardMetadataResponse.class, cardMetadataRequest, null);
    }

    private void validateCardMetadataRequest(final CardMetadataRequest cardMetadataRequest) {
        CheckoutUtils.validateParams("cardMetadataRequest", cardMetadataRequest);
    }
}
