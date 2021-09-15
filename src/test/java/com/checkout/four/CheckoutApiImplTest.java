package com.checkout.four;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CheckoutApiImplTest {

    @Mock
    private ApiClient apiClient;

    @Test
    void shouldInstantiateAndRetrieveClients() {
        final CheckoutApi checkoutApi = new CheckoutApiImpl(apiClient, mock(CheckoutConfiguration.class));
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.customersClient());
        assertNotNull(checkoutApi.disputesClient());
        assertNotNull(checkoutApi.instrumentsClient());
        assertNotNull(checkoutApi.riskClient());
        assertNotNull(checkoutApi.workflowsClient());
    }

}
