package com.checkout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.checkout.TestHelper.mockDefaultConfiguration;
import static com.checkout.TestHelper.mockFourConfiguration;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CheckoutApiTest {

    @Mock
    private ApiClient apiClient;

    private final CheckoutConfiguration defaultConfiguration = mockDefaultConfiguration();
    private final CheckoutConfiguration fourConfiguration = mockFourConfiguration();

    @Test
    void shouldInstantiateAndRetrieveClients() {
        final CheckoutApi checkoutApi = new CheckoutApiImpl(fourConfiguration);
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    void shouldInstantiateAndRetrieveClients_deprecatedConstructor() {
        final CheckoutApi checkoutApi = new CheckoutApiImpl(apiClient, defaultConfiguration);
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    void shouldInstantiateAndRetrieveClients_deprecatedStaticConstructor_1() {
        final CheckoutApi checkoutApi = CheckoutApiImpl.create(defaultConfiguration.getSecretKey(), true, defaultConfiguration.getPublicKey());
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    void shouldInstantiateAndRetrieveClients_deprecatedStaticConstructor_2() {
        final CheckoutApi checkoutApi = CheckoutApiImpl.create(defaultConfiguration.getSecretKey(), "uri", defaultConfiguration.getPublicKey());
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

}
