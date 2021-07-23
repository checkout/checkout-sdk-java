package com.checkout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.checkout.TestHelper.mockClassicConfiguration;
import static com.checkout.TestHelper.mockFourConfiguration;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutApiTest {

    @Mock
    private ApiClient apiClient;

    private final CheckoutConfiguration classicConfiguration = mockClassicConfiguration();
    private final CheckoutConfiguration fourConfiguration = mockFourConfiguration();

    @Test
    public void shouldInstantiateAndRetrieveClients() {
        CheckoutApi checkoutApi = new CheckoutApiImpl(fourConfiguration);
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    public void shouldInstantiateAndRetrieveClients_deprecatedConstructor() {
        CheckoutApi checkoutApi = new CheckoutApiImpl(apiClient, classicConfiguration);
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    public void shouldInstantiateAndRetrieveClients_deprecatedStaticConstructor_1() {
        CheckoutApi checkoutApi = CheckoutApiImpl.create(classicConfiguration.getSecretKey(), true, classicConfiguration.getPublicKey());
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

    @Test
    public void shouldInstantiateAndRetrieveClients_deprecatedStaticConstructor_2() {
        CheckoutApi checkoutApi = CheckoutApiImpl.create(classicConfiguration.getSecretKey(), "uri", classicConfiguration.getPublicKey());
        assertNotNull(checkoutApi.paymentsClient());
        assertNotNull(checkoutApi.sourcesClient());
        assertNotNull(checkoutApi.tokensClient());
        assertNotNull(checkoutApi.eventsClient());
        assertNotNull(checkoutApi.webhooksClient());
        assertNotNull(checkoutApi.instrumentsClient());
    }

}
