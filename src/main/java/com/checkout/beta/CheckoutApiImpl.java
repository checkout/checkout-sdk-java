package com.checkout.beta;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.customers.beta.CustomersClient;
import com.checkout.customers.beta.CustomersClientImpl;
import com.checkout.disputes.beta.DisputesClient;
import com.checkout.disputes.beta.DisputesClientImpl;
import com.checkout.instruments.beta.InstrumentsClient;
import com.checkout.instruments.beta.InstrumentsClientImpl;
import com.checkout.payments.beta.PaymentsClient;
import com.checkout.payments.beta.PaymentsClientImpl;
import com.checkout.tokens.beta.TokensClient;
import com.checkout.tokens.beta.TokensClientImpl;

public final class CheckoutApiImpl implements CheckoutApi {

    private final TokensClient tokensClient;
    private final PaymentsClient paymentsClient;
    private final CustomersClient customersClient;
    private final DisputesClient disputesClient;
    private final InstrumentsClient instrumentsClient;

    public CheckoutApiImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        this.tokensClient = new TokensClientImpl(apiClient, configuration);
        this.paymentsClient = new PaymentsClientImpl(apiClient, configuration);
        this.customersClient = new CustomersClientImpl(apiClient, configuration);
        this.disputesClient = new DisputesClientImpl(apiClient, configuration);
        this.instrumentsClient = new InstrumentsClientImpl(apiClient, configuration);
    }

    @Override
    public TokensClient tokensClient() {
        return tokensClient;
    }

    @Override
    public PaymentsClient paymentsClient() {
        return paymentsClient;
    }

    @Override
    public CustomersClient customersClient() {
        return customersClient;
    }

    @Override
    public DisputesClient disputesClient() {
        return disputesClient;
    }

    @Override
    public InstrumentsClient instrumentsClient() {
        return instrumentsClient;
    }

}
