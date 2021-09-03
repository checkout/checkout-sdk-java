package com.checkout.beta;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.checkout.TestHelper.mockFourConfiguration;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CheckoutApiTest {

    @Mock
    private ApiClient apiClient;

    private CheckoutApi checkoutApi;

    @BeforeEach
    void setup() {
        final CheckoutConfiguration checkoutConfiguration = mockFourConfiguration();
        this.checkoutApi = new CheckoutApiImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldRetrieveTokensClient() {
        assertNotNull(checkoutApi.tokensClient());
    }

}