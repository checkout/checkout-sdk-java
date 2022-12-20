package com.checkout;

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
        when(configuration.getEnvironment()).thenReturn(Environment.SANDBOX);
        when(configuration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        when(configuration.getExecutor()).thenReturn(mock(Executor.class));
        final CheckoutApi checkoutApi = new CheckoutApiImpl(configuration);
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.customersClient());
        assertNotNull(checkoutApi.disputesClient());
        assertNotNull(checkoutApi.instrumentsClient());
        assertNotNull(checkoutApi.riskClient());
        assertNotNull(checkoutApi.workflowsClient());
        assertNotNull(checkoutApi.accountsClient());
        assertNotNull(checkoutApi.sessionsClient());
        assertNotNull(checkoutApi.forexClient());
        assertNotNull(checkoutApi.hostedPaymentsClient());
        assertNotNull(checkoutApi.paymentLinksClient());
        assertNotNull(checkoutApi.forexClient());
        assertNotNull(checkoutApi.metadataClient());
        // APMs
        assertNotNull(checkoutApi.idealClient());
    }

}
