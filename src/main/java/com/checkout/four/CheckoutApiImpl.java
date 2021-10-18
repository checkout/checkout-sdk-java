package com.checkout.four;

import com.checkout.AbstractCheckoutApmApi;
import com.checkout.ApiClient;
import com.checkout.CheckoutApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.customers.four.CustomersClient;
import com.checkout.customers.four.CustomersClientImpl;
import com.checkout.disputes.four.DisputesClient;
import com.checkout.disputes.four.DisputesClientImpl;
import com.checkout.instruments.four.InstrumentsClient;
import com.checkout.instruments.four.InstrumentsClientImpl;
import com.checkout.marketplace.MarketplaceClient;
import com.checkout.marketplace.MarketplaceClientImpl;
import com.checkout.payments.four.PaymentsClient;
import com.checkout.payments.four.PaymentsClientImpl;
import com.checkout.risk.RiskClient;
import com.checkout.risk.RiskClientImpl;
import com.checkout.sessions.SessionsClient;
import com.checkout.sessions.SessionsClientImpl;
import com.checkout.tokens.four.TokensClient;
import com.checkout.tokens.four.TokensClientImpl;
import com.checkout.workflows.four.WorkflowsClient;
import com.checkout.workflows.four.WorkflowsClientImpl;

public class CheckoutApiImpl extends AbstractCheckoutApmApi implements CheckoutApi, CheckoutApiClient {

    private final TokensClient tokensClient;
    private final PaymentsClient paymentsClient;
    private final CustomersClient customersClient;
    private final DisputesClient disputesClient;
    private final InstrumentsClient instrumentsClient;
    private final RiskClient riskClient;
    private final WorkflowsClient workflowsClient;
    private final MarketplaceClient marketplaceClient;
    private final SessionsClient sessionsClient;

    public CheckoutApiImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration);
        this.tokensClient = new TokensClientImpl(apiClient, configuration);
        this.paymentsClient = new PaymentsClientImpl(apiClient, configuration);
        this.customersClient = new CustomersClientImpl(apiClient, configuration);
        this.disputesClient = new DisputesClientImpl(apiClient, configuration);
        this.instrumentsClient = new InstrumentsClientImpl(apiClient, configuration);
        this.riskClient = new RiskClientImpl(apiClient, configuration);
        this.workflowsClient = new WorkflowsClientImpl(apiClient, configuration);
        this.marketplaceClient = new MarketplaceClientImpl(apiClient, configuration);
        this.sessionsClient = new SessionsClientImpl(apiClient, configuration);
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

    @Override
    public RiskClient riskClient() {
        return riskClient;
    }

    @Override
    public WorkflowsClient workflowsClient() {
        return workflowsClient;
    }

    @Override
    public MarketplaceClient marketplaceClient() {
        return marketplaceClient;
    }

    @Override
    public SessionsClient sessionsClient() {
        return sessionsClient;
    }

}
