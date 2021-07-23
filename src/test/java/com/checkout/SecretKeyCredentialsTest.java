package com.checkout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.checkout.TestHelper.mockFourConfiguration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class SecretKeyCredentialsTest {

    @Test
    public void shouldNotCreateCheckoutConfiguration_invalidConfiguration() {
        try {
            SecretKeyCredentials.fromConfiguration(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("configuration must be not be null", e.getMessage());
        }
    }

    @Test
    public void shouldCreateCheckoutConfiguration() {
        final CheckoutConfiguration checkoutConfiguration = mockFourConfiguration();
        final SecretKeyCredentials publicKeyCredentials = SecretKeyCredentials.fromConfiguration(checkoutConfiguration);
        assertEquals("Bearer " + checkoutConfiguration.getSecretKey(), publicKeyCredentials.getAuthorizationHeader());
    }

    @Test
    public void toDeprecate_shouldCreateCheckoutConfiguration() {
        final CheckoutConfiguration checkoutConfiguration = mockFourConfiguration();
        final SecretKeyCredentials publicKeyCredentials = new SecretKeyCredentials(checkoutConfiguration);
        assertEquals("Bearer " + checkoutConfiguration.getSecretKey(), publicKeyCredentials.getAuthorizationHeader());
    }

    @Test
    public void toDeprecate_shouldNotCreateCheckoutConfiguration_invalidConfiguration() {
        try {
            new SecretKeyCredentials(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("configuration must be not be null", e.getMessage());
        }
    }

}