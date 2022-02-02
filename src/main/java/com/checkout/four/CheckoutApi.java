package com.checkout.four;

import com.checkout.customers.four.CustomersClient;
import com.checkout.disputes.DisputesClient;
import com.checkout.forex.four.ForexClient;
import com.checkout.instruments.four.InstrumentsClient;
import com.checkout.marketplace.MarketplaceClient;
import com.checkout.payments.four.PaymentsClient;
import com.checkout.risk.RiskClient;
import com.checkout.sessions.SessionsClient;
import com.checkout.tokens.TokensClient;
import com.checkout.workflows.four.WorkflowsClient;

public interface CheckoutApi extends CheckoutApmApi {

    TokensClient tokensClient();

    PaymentsClient paymentsClient();

    CustomersClient customersClient();

    DisputesClient disputesClient();

    InstrumentsClient instrumentsClient();

    RiskClient riskClient();

    WorkflowsClient workflowsClient();

    MarketplaceClient marketplaceClient();

    SessionsClient sessionsClient();

    ForexClient forexClient();

}

