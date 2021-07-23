package com.checkout.beta;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.checkout.TestHelper.mockFourConfiguration;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutApiTest {

    @Mock
    private ApiClient apiClient;

    private CheckoutApi checkoutApi;

    @Before
    public void setup() {
        CheckoutConfiguration checkoutConfiguration = mockFourConfiguration();
        this.checkoutApi = new CheckoutApiImpl(apiClient, checkoutConfiguration);
    }

    @Test
    public void shouldRetrieveTokensClient() {
        assertNotNull(checkoutApi.tokensClient());
    }

}