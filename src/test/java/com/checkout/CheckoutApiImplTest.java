package com.checkout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CheckoutApiImplTest {

    @Test
    void shouldInstantiateAndRetrieveClients() {
        final CheckoutApi checkoutApi = new CheckoutApiImpl(mock(ApiClient.class), mock(CheckoutConfiguration.class));
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
        assertNotNull(checkoutApi.paymentLinksClient());
        assertNotNull(checkoutApi.hostedPaymentsClient());
        assertNotNull(checkoutApi.customersClient());
        assertNotNull(checkoutApi.disputesClient());
        assertNotNull(checkoutApi.reconciliationClient());
        assertNotNull(checkoutApi.riskClient());
        // APMs
        assertNotNull(checkoutApi.balotoClient());
        assertNotNull(checkoutApi.fawryClient());
        assertNotNull(checkoutApi.idealClient());
        assertNotNull(checkoutApi.klarnaClient());
        assertNotNull(checkoutApi.oxxoClient());
        assertNotNull(checkoutApi.pagoFacilClient());
        assertNotNull(checkoutApi.rapiPagoClient());
        assertNotNull(checkoutApi.sepaClient());

    }

}
