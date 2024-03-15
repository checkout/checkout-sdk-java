package com.checkout.metadata;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.metadata.card.CardMetadataRequest;
import com.checkout.metadata.card.CardMetadataResponse;

import java.util.concurrent.CompletableFuture;

public class MetadataClientImpl extends AbstractClient implements MetadataClient {

    public MetadataClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<CardMetadataResponse> requestCardMetadata(final CardMetadataRequest cardMetadataRequest) {
        CheckoutUtils.validateParams("cardMetadataRequest", cardMetadataRequest);
        return apiClient.postAsync("metadata/card", sdkAuthorization(), CardMetadataResponse.class, cardMetadataRequest, null);
    }

}
