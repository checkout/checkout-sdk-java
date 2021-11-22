package com.checkout;

import com.checkout.customers.CustomersClient;
import com.checkout.customers.CustomersClientImpl;
import com.checkout.disputes.DisputesClient;
import com.checkout.disputes.DisputesClientImpl;
import com.checkout.events.EventsClient;
import com.checkout.events.EventsClientImpl;
import com.checkout.instruments.InstrumentsClient;
import com.checkout.instruments.InstrumentsClientImpl;
import com.checkout.payments.PaymentsClient;
import com.checkout.payments.PaymentsClientImpl;
import com.checkout.payments.hosted.HostedPaymentsClient;
import com.checkout.payments.hosted.HostedPaymentsClientImpl;
import com.checkout.payments.links.PaymentLinksClient;
import com.checkout.payments.links.PaymentLinksClientImpl;
import com.checkout.reconciliation.ReconciliationClient;
import com.checkout.reconciliation.ReconciliationClientImpl;
import com.checkout.risk.RiskClient;
import com.checkout.risk.RiskClientImpl;
import com.checkout.sources.SourcesClient;
import com.checkout.sources.SourcesClientImpl;
import com.checkout.tokens.TokensClient;
import com.checkout.tokens.TokensClientImpl;
import com.checkout.webhooks.WebhooksClient;
import com.checkout.webhooks.WebhooksClientImpl;

public class CheckoutApiImpl extends AbstractCheckoutApmApi implements CheckoutApi, CheckoutApiClient {

    private final PaymentsClient paymentsClient;
    private final SourcesClient sourcesClient;
    private final TokensClient tokensClient;
    private final WebhooksClient webhooksClient;
    private final EventsClient eventsClient;
    private final InstrumentsClient instrumentsClient;
    private final PaymentLinksClient paymentLinksClient;
    private final HostedPaymentsClient hostedPaymentsClient;
    private final CustomersClient customersClient;
    private final DisputesClient disputesClient;
    private final ReconciliationClient reconciliationClient;
    private final RiskClient riskClient;

    public CheckoutApiImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration);
        this.paymentsClient = new PaymentsClientImpl(apiClient, configuration);
        this.sourcesClient = new SourcesClientImpl(apiClient, configuration);
        this.tokensClient = new TokensClientImpl(apiClient, configuration);
        this.webhooksClient = new WebhooksClientImpl(apiClient, configuration);
        this.eventsClient = new EventsClientImpl(apiClient, configuration);
        this.instrumentsClient = new InstrumentsClientImpl(apiClient, configuration);
        this.paymentLinksClient = new PaymentLinksClientImpl(apiClient, configuration);
        this.hostedPaymentsClient = new HostedPaymentsClientImpl(apiClient, configuration);
        this.customersClient = new CustomersClientImpl(apiClient, configuration);
        this.disputesClient = new DisputesClientImpl(apiClient, configuration);
        this.reconciliationClient = new ReconciliationClientImpl(apiClient, configuration);
        this.riskClient = new RiskClientImpl(apiClient, configuration);
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

    @Override
    public WebhooksClient webhooksClient() {
        return webhooksClient;
    }

    @Override
    public EventsClient eventsClient() {
        return eventsClient;
    }

    @Override
    public InstrumentsClient instrumentsClient() {
        return instrumentsClient;
    }

    @Override
    public PaymentLinksClient paymentLinksClient() {
        return paymentLinksClient;
    }

    @Override
    public HostedPaymentsClient hostedPaymentsClient() {
        return hostedPaymentsClient;
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
    public ReconciliationClient reconciliationClient() {
        return reconciliationClient;
    }

    @Override
    public RiskClient riskClient() {
        return riskClient;
    }

}
