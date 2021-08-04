package com.checkout.beta;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.payments.beta.PaymentsClient;
import com.checkout.payments.beta.PaymentsClientImpl;
import com.checkout.tokens.beta.TokensClient;
import com.checkout.tokens.beta.TokensClientImpl;

public final class CheckoutApiImpl implements CheckoutApi {

    private final TokensClient tokensClient;
    private final PaymentsClient paymentsClient;

    public CheckoutApiImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        this.tokensClient = new TokensClientImpl(apiClient, configuration);
        this.paymentsClient = new PaymentsClientImpl(apiClient, configuration);
    }

    @Override
    public TokensClient tokensClient() {
        return tokensClient;
    }

    @Override
    public PaymentsClient paymentsClient() {
        return paymentsClient;
    }

}
