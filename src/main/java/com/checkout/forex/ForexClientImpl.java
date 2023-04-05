package com.checkout.forex;

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

    @Override
    public CompletableFuture<RatesQueryResponse> getRates(RatesQueryFilter ratesQuery) {
        CheckoutUtils.validateParams("ratesQuery", ratesQuery);
        return apiClient.queryAsync("forex/rates", sdkAuthorization(), ratesQuery, RatesQueryResponse.class);
    }

}
