package com.checkout.sources;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.common.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SourcesClientImpl implements SourcesClient {
    private static final Map<Integer, Class<? extends Resource>> SOURCE_RESPONSE_MAPPINGS = new HashMap<>();

    static {
        SOURCE_RESPONSE_MAPPINGS.put(201, SourceProcessed.class);
    }

    private final ApiClient apiClient;
    private final ApiCredentials credentials;

    public SourcesClientImpl(ApiClient apiClient, CheckoutConfiguration configuration) {
        if (apiClient == null) {
            throw new IllegalArgumentException("apiClient must not be null");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }

        this.apiClient = apiClient;
        credentials = new SecretKeyCredentials(configuration);
    }

    @Override
    public CompletableFuture<SourceResponse> requestAsync(SourceRequest sourceRequest) {
        return requestSourceAsync(sourceRequest, SOURCE_RESPONSE_MAPPINGS);
    }

    private CompletableFuture<SourceResponse> requestSourceAsync(SourceRequest sourceRequest, Map<Integer, Class<? extends Resource>> resultTypeMappings) {
        final String path = "sources";
        return apiClient.postAsync(path, credentials, resultTypeMappings, sourceRequest, null)
                .thenApply(it -> {
                    if (it instanceof SourceProcessed) {
                        return SourceResponse.from((SourceProcessed) it);
                    } else {
                        throw new IllegalStateException("Expected SourceProcessed but was " + it.getClass());
                    }
                });
    }
}