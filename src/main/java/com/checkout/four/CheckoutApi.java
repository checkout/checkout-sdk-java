package com.checkout.four;

import com.checkout.customers.four.CustomersClient;
import com.checkout.disputes.four.DisputesClient;
import com.checkout.instruments.four.InstrumentsClient;
import com.checkout.marketplace.MarketplaceClient;
import com.checkout.payments.four.PaymentsClient;
import com.checkout.risk.RiskClient;
import com.checkout.sessions.SessionsClient;
import com.checkout.tokens.four.TokensClient;
import com.checkout.workflows.four.WorkflowsClient;

public interface CheckoutApi {

    TokensClient tokensClient();

    PaymentsClient paymentsClient();

    CustomersClient customersClient();

    DisputesClient disputesClient();

    InstrumentsClient instrumentsClient();

    RiskClient riskClient();

    WorkflowsClient workflowsClient();

    MarketplaceClient marketplaceClient();

    SessionsClient sessionsClient();

}

