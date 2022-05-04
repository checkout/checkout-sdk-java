package com.checkout.sources;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class SourcesClientImpl extends AbstractClient implements SourcesClient {

    private static final String SOURCES_PATH = "sources";

    public SourcesClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<SepaSourceResponse> createSepaSource(final SepaSourceRequest sepaSourceRequest) {
        validateParams("sepaSourceRequest", sepaSourceRequest);
        return apiClient.postAsync(SOURCES_PATH, sdkAuthorization(), SepaSourceResponse.class, sepaSourceRequest, null);
    }
}