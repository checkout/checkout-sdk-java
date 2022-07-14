package com.checkout.previous;

import com.checkout.customers.previous.CustomersClient;
import com.checkout.disputes.DisputesClient;
import com.checkout.events.previous.EventsClient;
import com.checkout.instruments.previous.InstrumentsClient;
import com.checkout.payments.previous.PaymentsClient;
import com.checkout.payments.hosted.HostedPaymentsClient;
import com.checkout.payments.links.PaymentLinksClient;
import com.checkout.reconciliation.previous.ReconciliationClient;
import com.checkout.risk.RiskClient;
import com.checkout.sources.previous.SourcesClient;
import com.checkout.tokens.TokensClient;
import com.checkout.webhooks.previous.WebhooksClient;

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
