package com.checkout;

import com.checkout.payments.PaymentsClient;
import com.checkout.payments.PaymentsClientImpl;
import com.checkout.sources.SourcesClient;
import com.checkout.sources.SourcesClientImpl;
import com.checkout.tokens.TokensClient;
import com.checkout.tokens.TokensClientImpl;

public class CheckoutApiImpl implements CheckoutApi {

    private PaymentsClient paymentsClient;
    private SourcesClient sourcesClient;
    private TokensClient tokensClient;

    public CheckoutApiImpl(ApiClient apiClient, CheckoutConfiguration configuration) {
        paymentsClient = new PaymentsClientImpl(apiClient, configuration);
        sourcesClient = new SourcesClientImpl(apiClient, configuration);
        tokensClient = new TokensClientImpl(apiClient, configuration);
    }

    public static CheckoutApi create(String secretKey, boolean useSandbox, String publicKey) {
        CheckoutConfiguration configuration = new CheckoutConfiguration(secretKey, useSandbox);
        configuration.setPublicKey(publicKey);

        ApiClient apiClient = new ApiClientImpl(configuration);
        return new CheckoutApiImpl(apiClient, configuration);
    }

    public static CheckoutApi create(String secretKey, String uri, String publicKey) {
        CheckoutConfiguration configuration = new CheckoutConfiguration(secretKey, uri);
        configuration.setPublicKey(publicKey);

        ApiClient apiClient = new ApiClientImpl(configuration);
        return new CheckoutApiImpl(apiClient, configuration);
    }

    @Override
    public PaymentsClient paymentsClient() {
        return paymentsClient;
    }

    @Override
    public SourcesClient sourcesClient() {
        return sourcesClient;
    }

    @Override
    public TokensClient tokensClient() {
        return tokensClient;
    }
}