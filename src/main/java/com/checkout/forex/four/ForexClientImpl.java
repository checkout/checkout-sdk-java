package com.checkout.forex.four;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;

import java.util.concurrent.CompletableFuture;

public class ForexClientImpl extends AbstractClient implements ForexClient {

    public ForexClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
    }

    @Override
    public CompletableFuture<QuoteResponse> requestQuote(final QuoteRequest quoteRequest) {
        CheckoutUtils.validateParams("quoteRequest", quoteRequest);
        return apiClient.postAsync("forex/quotes", sdkAuthorization(), QuoteResponse.class, quoteRequest, null);
    }

}
