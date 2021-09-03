package com.checkout;

import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.mockFourConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class PublicKeyCredentialsTest {

    @Test
    void shouldNotCreateCheckoutConfiguration_invalidConfiguration() {
        try {
            PublicKeyCredentials.fromConfiguration(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("configuration cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateCheckoutConfiguration() {
        final CheckoutConfiguration checkoutConfiguration = mockFourConfiguration();
        final PublicKeyCredentials publicKeyCredentials = PublicKeyCredentials.fromConfiguration(checkoutConfiguration);
        assertEquals("Bearer " + checkoutConfiguration.getPublicKey(), publicKeyCredentials.getAuthorizationHeader());
    }

    @Test
    void toDeprecate_shouldCreateCheckoutConfiguration() {
        final CheckoutConfiguration checkoutConfiguration = mockFourConfiguration();
        final PublicKeyCredentials publicKeyCredentials = new PublicKeyCredentials(checkoutConfiguration);
        assertEquals("Bearer " + checkoutConfiguration.getPublicKey(), publicKeyCredentials.getAuthorizationHeader());
    }

    @Test
    void toDeprecate_shouldNotCreateCheckoutConfiguration_invalidConfiguration() {
        try {
            new PublicKeyCredentials(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("configuration cannot be null", e.getMessage());
        }
    }

}