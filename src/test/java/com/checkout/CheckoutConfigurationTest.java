package com.checkout;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CheckoutConfigurationTest {

    @Test
    void shouldFailCreatingConfiguration() {
        try {
            final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);
            new CheckoutConfiguration(credentials, (Environment) null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldFailCreatingConfiguration_invalidURI() {
        try {
            final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);
            new CheckoutConfiguration(credentials, (URI) null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("uri cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateConfiguration() throws URISyntaxException {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new CheckoutConfiguration(credentials, Environment.PRODUCTION);
        assertEquals(Environment.PRODUCTION.getUri(), configuration.getUri());

        final CheckoutConfiguration configuration2 = new CheckoutConfiguration(credentials, new URI("https://www.test.checkout.com/"));
        assertEquals("https://www.test.checkout.com/", configuration2.getUri());

    }

    @Test
    void shouldCreateConfigurationForProd() throws URISyntaxException {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new CheckoutConfiguration(credentials, Environment.PRODUCTION);
        assertEquals(Environment.PRODUCTION.getUri(), configuration.getUri());

        final CheckoutConfiguration configuration2 = new CheckoutConfiguration(credentials, new URI("https://www.test.checkout.com/"));
        assertEquals("https://www.test.checkout.com/", configuration2.getUri());

    }

}
