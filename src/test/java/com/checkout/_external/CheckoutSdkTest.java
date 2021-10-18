package com.checkout._external;

import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static com.checkout.TestHelper.VALID_FOUR_PK;
import static com.checkout.TestHelper.VALID_FOUR_SK;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// This class was left in _external package intentionally to assert that
// visibility modifiers wont affect SDK usage on a different package.
class CheckoutSdkTest {

    @Test
    void shouldCreateDefaultSdk() {

        final com.checkout.CheckoutApi defaultCheckoutApi = CheckoutSdk.defaultSdk().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(defaultCheckoutApi);
        assertTrue(defaultCheckoutApi.paymentsClient() instanceof com.checkout.payments.PaymentsClientImpl);
        assertTrue(defaultCheckoutApi.sourcesClient() instanceof com.checkout.sources.SourcesClientImpl);
        assertTrue(defaultCheckoutApi.tokensClient() instanceof com.checkout.tokens.TokensClientImpl);
        assertTrue(defaultCheckoutApi.eventsClient() instanceof com.checkout.events.EventsClientImpl);
        assertTrue(defaultCheckoutApi.webhooksClient() instanceof com.checkout.webhooks.WebhooksClientImpl);
        assertTrue(defaultCheckoutApi.instrumentsClient() instanceof com.checkout.instruments.InstrumentsClientImpl);
        assertTrue(defaultCheckoutApi.paymentLinksClient() instanceof com.checkout.payments.links.PaymentLinksClientImpl);
        assertTrue(defaultCheckoutApi.hostedPaymentsClient() instanceof com.checkout.payments.hosted.HostedPaymentsClientImpl);
        assertTrue(defaultCheckoutApi.customersClient() instanceof com.checkout.customers.CustomersClientImpl);
        assertTrue(defaultCheckoutApi.disputesClient() instanceof com.checkout.disputes.DisputesClientImpl);
        assertTrue(defaultCheckoutApi.reconciliationClient() instanceof com.checkout.reconciliation.ReconciliationClientImpl);
        assertTrue(defaultCheckoutApi.riskClient() instanceof com.checkout.risk.RiskClientImpl);
        assertTrue(defaultCheckoutApi.balotoClient() instanceof com.checkout.apm.baloto.BalotoClientImpl);
        assertTrue(defaultCheckoutApi.fawryClient() instanceof com.checkout.apm.fawry.FawryClientImpl);
        assertTrue(defaultCheckoutApi.idealClient() instanceof com.checkout.apm.ideal.IdealClientImpl);
        assertTrue(defaultCheckoutApi.klarnaClient() instanceof com.checkout.apm.klarna.KlarnaClientImpl);
        assertTrue(defaultCheckoutApi.oxxoClient() instanceof com.checkout.apm.oxxo.OxxoClientImpl);
        assertTrue(defaultCheckoutApi.pagoFacilClient() instanceof com.checkout.apm.pagofacil.PagoFacilImpl);
        assertTrue(defaultCheckoutApi.rapiPagoClient() instanceof com.checkout.apm.rapipago.RapiPagoClientImpl);
        assertTrue(defaultCheckoutApi.sepaClient() instanceof com.checkout.apm.sepa.SepaClientImpl);

    }

    @Test
    void shouldCreateFourSdk() {

        final com.checkout.four.CheckoutApi fourCheckoutApi = CheckoutSdk.fourSdk().staticKeys()
                .publicKey(VALID_FOUR_PK)
                .secretKey(VALID_FOUR_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(fourCheckoutApi);
        assertTrue(fourCheckoutApi.tokensClient() instanceof com.checkout.tokens.four.TokensClientImpl);
        assertTrue(fourCheckoutApi.paymentsClient() instanceof com.checkout.payments.four.PaymentsClientImpl);
        assertTrue(fourCheckoutApi.customersClient() instanceof com.checkout.customers.four.CustomersClientImpl);
        assertTrue(fourCheckoutApi.disputesClient() instanceof com.checkout.disputes.four.DisputesClientImpl);
        assertTrue(fourCheckoutApi.instrumentsClient() instanceof com.checkout.instruments.four.InstrumentsClientImpl);
        assertTrue(fourCheckoutApi.riskClient() instanceof com.checkout.risk.RiskClientImpl);
        assertTrue(fourCheckoutApi.workflowsClient() instanceof com.checkout.workflows.four.WorkflowsClientImpl);

    }


}