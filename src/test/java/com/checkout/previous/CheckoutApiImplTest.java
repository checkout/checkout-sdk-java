package com.checkout.previous;

import com.checkout.CheckoutConfiguration;
import com.checkout.Environment;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutApiImplTest {

    @Test
    void shouldInstantiateAndRetrieveClients() {
        final CheckoutConfiguration configuration = mock(CheckoutConfiguration.class);
        when(configuration.getHttpClientBuilder()).thenReturn(mock(HttpClientBuilder.class));
        when(configuration.getExecutor()).thenReturn(mock(Executor.class));
        when(configuration.getEnvironment()).thenReturn(Environment.SANDBOX);
        final CheckoutApi checkoutApi = new CheckoutApiImpl(configuration);
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
        assertNotNull(checkoutApi.idealClient());
        assertNotNull(checkoutApi.klarnaClient());
        assertNotNull(checkoutApi.sepaClient());

    }

}
