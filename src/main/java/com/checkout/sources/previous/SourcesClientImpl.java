package com.checkout.sources.previous;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.util.concurrent.CompletableFuture;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

public class SourcesClientImpl extends AbstractClient implements SourcesClient {

    private static final String SOURCES_PATH = "sources";

    public SourcesClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<SepaSourceResponse> createSepaSource(final SepaSourceRequest sepaSourceRequest) {
        validateSepaSourceRequest(sepaSourceRequest);
        return apiClient.postAsync(SOURCES_PATH, sdkAuthorization(), SepaSourceResponse.class, sepaSourceRequest, null);
    }

    // Synchronous methods
    @Override
    public SepaSourceResponse createSepaSourceSync(final SepaSourceRequest sepaSourceRequest) {
        validateSepaSourceRequest(sepaSourceRequest);
        return apiClient.post(SOURCES_PATH, sdkAuthorization(), SepaSourceResponse.class, sepaSourceRequest, null);
    }

    // Common methods
    protected void validateSepaSourceRequest(final SepaSourceRequest sepaSourceRequest) {
        validateParams("sepaSourceRequest", sepaSourceRequest);
    }
}