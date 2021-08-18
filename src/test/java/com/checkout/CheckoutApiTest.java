package com.checkout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.checkout.TestHelper.mockClassicConfiguration;
import static com.checkout.TestHelper.mockFourConfiguration;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CheckoutApiTest {

    @Mock
    private ApiClient apiClient;

    private final CheckoutConfiguration classicConfiguration = mockClassicConfiguration();
    private final CheckoutConfiguration fourConfiguration = mockFourConfiguration();

    @Test
    public void shouldInstantiateAndRetrieveClients() {
        final CheckoutApi checkoutApi = new CheckoutApiImpl(fourConfiguration);
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    public void shouldInstantiateAndRetrieveClients_deprecatedConstructor() {
        final CheckoutApi checkoutApi = new CheckoutApiImpl(apiClient, classicConfiguration);
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    public void shouldInstantiateAndRetrieveClients_deprecatedStaticConstructor_1() {
        final CheckoutApi checkoutApi = CheckoutApiImpl.create(classicConfiguration.getSecretKey(), true, classicConfiguration.getPublicKey());
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    public void shouldInstantiateAndRetrieveClients_deprecatedStaticConstructor_2() {
        final CheckoutApi checkoutApi = CheckoutApiImpl.create(classicConfiguration.getSecretKey(), "uri", classicConfiguration.getPublicKey());
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

}
