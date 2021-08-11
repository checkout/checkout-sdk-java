package com.checkout.sources;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.AbstractClient;
import com.checkout.common.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SourcesClientImpl extends AbstractClient implements SourcesClient {
    private static final Map<Integer, Class<? extends Resource>> SOURCE_RESPONSE_MAPPINGS = new HashMap<>();

    static {
        SOURCE_RESPONSE_MAPPINGS.put(201, SourceProcessed.class);
    }

    public SourcesClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<SourceResponse> requestAsync(final SourceRequest sourceRequest) {
        return requestSourceAsync(sourceRequest, SOURCE_RESPONSE_MAPPINGS);
    }

    private CompletableFuture<SourceResponse> requestSourceAsync(final SourceRequest sourceRequest, final Map<Integer, Class<? extends Resource>> resultTypeMappings) {
        final String path = "sources";
        return apiClient.postAsync(path, apiCredentials, resultTypeMappings, sourceRequest, null)
                .thenApply(it -> {
                    if (it instanceof SourceProcessed) {
                        return SourceResponse.from((SourceProcessed) it);
                    } else {
                        throw new IllegalStateException("Expected SourceProcessed but was " + it.getClass());
                    }
                });
    }
}