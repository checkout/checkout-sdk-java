package com.checkout._external;

import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.accounts.AccountsClientImpl;
import com.checkout.apm.previous.klarna.KlarnaClientImpl;
import com.checkout.apm.previous.sepa.SepaClientImpl;
import com.checkout.balances.BalancesClientImpl;
import com.checkout.customers.previous.CustomersClientImpl;
import com.checkout.events.previous.EventsClientImpl;
import com.checkout.forex.ForexClientImpl;
import com.checkout.instruments.previous.InstrumentsClientImpl;
import com.checkout.payments.previous.PaymentsClientImpl;
import com.checkout.payments.hosted.HostedPaymentsClientImpl;
import com.checkout.payments.links.PaymentLinksClientImpl;
import com.checkout.previous.CheckoutApi;
import com.checkout.reconciliation.previous.ReconciliationClientImpl;
import com.checkout.sessions.SessionsClientImpl;
import com.checkout.sources.previous.SourcesClientImpl;
import com.checkout.transfers.TransfersClientImpl;
import com.checkout.webhooks.previous.WebhooksClientImpl;
import com.checkout.workflows.WorkflowsClientImpl;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.VALID_PREVIOUS_PK;
import static com.checkout.TestHelper.VALID_PREVIOUS_SK;
import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// This class was left in _external package intentionally to assert that
// visibility modifiers wont affect SDK usage on a different package.
class CheckoutSdkTest {

    @Test
    void shouldCreatePreviousSdk() {

        final CheckoutApi defaultCheckoutApi = CheckoutSdk.builder().previous().staticKeys()
                .publicKey(VALID_PREVIOUS_PK)
                .secretKey(VALID_PREVIOUS_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(defaultCheckoutApi);
        assertTrue(defaultCheckoutApi.paymentsClient() instanceof PaymentsClientImpl);
        assertTrue(defaultCheckoutApi.sourcesClient() instanceof SourcesClientImpl);
        assertTrue(defaultCheckoutApi.tokensClient() instanceof com.checkout.tokens.TokensClientImpl);
        assertTrue(defaultCheckoutApi.eventsClient() instanceof EventsClientImpl);
        assertTrue(defaultCheckoutApi.webhooksClient() instanceof WebhooksClientImpl);
        assertTrue(defaultCheckoutApi.instrumentsClient() instanceof InstrumentsClientImpl);
        assertTrue(defaultCheckoutApi.paymentLinksClient() instanceof PaymentLinksClientImpl);
        assertTrue(defaultCheckoutApi.hostedPaymentsClient() instanceof HostedPaymentsClientImpl);
        assertTrue(defaultCheckoutApi.customersClient() instanceof CustomersClientImpl);
        assertTrue(defaultCheckoutApi.disputesClient() instanceof com.checkout.disputes.DisputesClientImpl);
        assertTrue(defaultCheckoutApi.reconciliationClient() instanceof ReconciliationClientImpl);
        assertTrue(defaultCheckoutApi.riskClient() instanceof com.checkout.risk.RiskClientImpl);
        assertTrue(defaultCheckoutApi.idealClient() instanceof com.checkout.apm.ideal.IdealClientImpl);
        assertTrue(defaultCheckoutApi.klarnaClient() instanceof KlarnaClientImpl);
        assertTrue(defaultCheckoutApi.sepaClient() instanceof SepaClientImpl);

    }

    @Test
    void shouldCreateSdk() {

        final com.checkout.CheckoutApi checkoutApi = CheckoutSdk.builder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi);
        assertTrue(checkoutApi.tokensClient() instanceof com.checkout.tokens.TokensClientImpl);
        assertTrue(checkoutApi.paymentsClient() instanceof com.checkout.payments.PaymentsClientImpl);
        assertTrue(checkoutApi.customersClient() instanceof com.checkout.customers.CustomersClientImpl);
        assertTrue(checkoutApi.disputesClient() instanceof com.checkout.disputes.DisputesClientImpl);
        assertTrue(checkoutApi.instrumentsClient() instanceof com.checkout.instruments.InstrumentsClientImpl);
        assertTrue(checkoutApi.riskClient() instanceof com.checkout.risk.RiskClientImpl);
        assertTrue(checkoutApi.workflowsClient() instanceof WorkflowsClientImpl);
        assertTrue(checkoutApi.sessionsClient() instanceof SessionsClientImpl);
        assertTrue(checkoutApi.accountsClient() instanceof AccountsClientImpl);
        assertTrue(checkoutApi.forexClient() instanceof ForexClientImpl);
        assertTrue(checkoutApi.paymentLinksClient() instanceof PaymentLinksClientImpl);
        assertTrue(checkoutApi.hostedPaymentsClient() instanceof HostedPaymentsClientImpl);
        assertTrue(checkoutApi.balancesClient() instanceof BalancesClientImpl);
        assertTrue(checkoutApi.transfersClient() instanceof TransfersClientImpl);

    }


}