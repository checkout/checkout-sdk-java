package com.checkout;

import com.checkout.accounts.AccountsClient;
import com.checkout.balances.BalancesClient;
import com.checkout.customers.CustomersClient;
import com.checkout.disputes.DisputesClient;
import com.checkout.financial.FinancialClient;
import com.checkout.forex.ForexClient;
import com.checkout.forward.ForwardClient;
import com.checkout.handlepaymentsandpayouts.flow.FlowClient;
import com.checkout.instruments.InstrumentsClient;
import com.checkout.issuing.IssuingClient;
import com.checkout.metadata.MetadataClient;
import com.checkout.payments.PaymentsClient;
import com.checkout.payments.contexts.PaymentContextsClient;
import com.checkout.payments.hosted.HostedPaymentsClient;
import com.checkout.payments.links.PaymentLinksClient;
import com.checkout.reports.ReportsClient;
import com.checkout.risk.RiskClient;
import com.checkout.sessions.SessionsClient;
import com.checkout.tokens.TokensClient;
import com.checkout.transfers.TransfersClient;
import com.checkout.workflows.WorkflowsClient;

public interface CheckoutApi extends CheckoutApmApi {

    TokensClient tokensClient();

    PaymentsClient paymentsClient();

    CustomersClient customersClient();

    DisputesClient disputesClient();

    InstrumentsClient instrumentsClient();

    RiskClient riskClient();

    WorkflowsClient workflowsClient();

    AccountsClient accountsClient();

    SessionsClient sessionsClient();

    ForexClient forexClient();

    PaymentLinksClient paymentLinksClient();

    HostedPaymentsClient hostedPaymentsClient();

    BalancesClient balancesClient();

    TransfersClient transfersClient();

    ReportsClient reportsClient();

    MetadataClient metadataClient();

    FinancialClient financialClient();

    IssuingClient issuingClient();

    PaymentContextsClient paymentContextsClient();

    FlowClient flowClient();

    ForwardClient forwardClient();

}
