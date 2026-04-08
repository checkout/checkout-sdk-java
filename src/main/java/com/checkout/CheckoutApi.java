package com.checkout;

import com.checkout.accounts.AccountsClient;
import com.checkout.agenticcommerce.AgenticCommerceClient;
import com.checkout.balances.BalancesClient;
import com.checkout.customers.CustomersClient;
import com.checkout.disputes.DisputesClient;
import com.checkout.financial.FinancialClient;
import com.checkout.forex.ForexClient;
import com.checkout.forward.ForwardClient;
import com.checkout.handlepaymentsandpayouts.flow.FlowClient;
import com.checkout.handlepaymentsandpayouts.setups.PaymentSetupsClient;
import com.checkout.compliance.ComplianceClient;
import com.checkout.handlepaymentsandpayouts.applepay.ApplePayClient;
import com.checkout.handlepaymentsandpayouts.googlepay.GooglePayClient;
import com.checkout.identities.faceauthentications.FaceAuthenticationClient;
import com.checkout.identities.applicants.ApplicantClient;
import com.checkout.identities.identityverification.IdentityVerificationClient;
import com.checkout.identities.iddocumentverification.IdDocumentVerificationClient;
import com.checkout.identities.amlscreening.AmlScreeningClient;
import com.checkout.instruments.InstrumentsClient;
import com.checkout.issuing.IssuingClient;
import com.checkout.metadata.MetadataClient;
import com.checkout.networktokens.NetworkTokensClient;
import com.checkout.paymentmethods.PaymentMethodsClient;
import com.checkout.payments.PaymentsClient;
import com.checkout.payments.contexts.PaymentContextsClient;
import com.checkout.payments.hosted.HostedPaymentsClient;
import com.checkout.payments.links.PaymentLinksClient;
import com.checkout.reports.ReportsClient;
import com.checkout.risk.RiskClient;
import com.checkout.sessions.SessionsClient;
import com.checkout.standaloneaccountupdater.StandaloneAccountUpdaterClient;
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

    PaymentMethodsClient paymentMethodsClient();

    HostedPaymentsClient hostedPaymentsClient();

    BalancesClient balancesClient();

    TransfersClient transfersClient();

    ReportsClient reportsClient();

    MetadataClient metadataClient();

    FinancialClient financialClient();

    IssuingClient issuingClient();

    PaymentContextsClient paymentContextsClient();

    FlowClient flowClient();

    PaymentSetupsClient paymentSetupsClient();

    ApplePayClient applePayClient();

    GooglePayClient googlePayClient();

    ComplianceClient complianceClient();

    ForwardClient forwardClient();

    FaceAuthenticationClient faceAuthenticationClient();

    ApplicantClient applicantClient();

    IdentityVerificationClient identityVerificationClient();

    IdDocumentVerificationClient idDocumentVerificationClient();

    AmlScreeningClient amlScreeningClient();

    NetworkTokensClient networkTokensClient();

    StandaloneAccountUpdaterClient standaloneAccountUpdaterClient();

    /**
     * Returns the client for the Agentic Commerce Protocol (Beta).
     * Use to create delegated payment tokens for AI-agent-initiated payments.
     */
    AgenticCommerceClient agenticCommerceClient();

}
