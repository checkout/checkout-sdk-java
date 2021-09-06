package com.checkout;

import com.checkout.customers.CustomersClient;
import com.checkout.disputes.DisputesClient;
import com.checkout.events.EventsClient;
import com.checkout.instruments.InstrumentsClient;
import com.checkout.payments.PaymentsClient;
import com.checkout.payments.hosted.HostedPaymentsClient;
import com.checkout.payments.links.PaymentLinksClient;
import com.checkout.reconciliation.ReconciliationClient;
import com.checkout.risk.RiskClient;
import com.checkout.sources.SourcesClient;
import com.checkout.tokens.TokensClient;
import com.checkout.webhooks.WebhooksClient;

public interface CheckoutApi extends CheckoutApmApi {

    PaymentsClient paymentsClient();

    SourcesClient sourcesClient();

    TokensClient tokensClient();

    WebhooksClient webhooksClient();

    EventsClient eventsClient();

    InstrumentsClient instrumentsClient();

    PaymentLinksClient paymentLinksClient();

    HostedPaymentsClient hostedPaymentsClient();

    CustomersClient customersClient();

    DisputesClient disputesClient();

    ReconciliationClient reconciliationClient();

    RiskClient riskClient();

}
