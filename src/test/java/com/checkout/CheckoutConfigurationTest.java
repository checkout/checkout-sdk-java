package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;

import static com.checkout.TestHelper.INVALID_DEFAULT_SK;
import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static com.checkout.TestHelper.VALID_FOUR_PK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

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
    void deprecated_shouldFailCreatingConfiguration_default_invalidURI() {
        try {
            new CheckoutConfiguration(VALID_FOUR_PK, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("uri cannot be null", e.getMessage());
        }
    }

    @Test
    void deprecated_shouldFailCreatingConfiguration_invalidKeys() {
        try {
            new CheckoutConfiguration(INVALID_DEFAULT_SK, true);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void deprecated_shouldCreateConfiguration() {

        final CheckoutConfiguration configuration1 = new CheckoutConfiguration(VALID_DEFAULT_SK, true);
        assertEquals(Environment.SANDBOX.getUri(), configuration1.getUri());

        final CheckoutConfiguration configuration2 = new CheckoutConfiguration(VALID_DEFAULT_SK, false, VALID_DEFAULT_PK);
        assertEquals(Environment.PRODUCTION.getUri(), configuration2.getUri());

        final HttpClientBuilder builder = mock(HttpClientBuilder.class);

        final CheckoutConfiguration configuration3 = new CheckoutConfiguration(VALID_DEFAULT_SK, "www.google.com");
        configuration3.setApacheHttpClientBuilder(builder);
        assertEquals("www.google.com", configuration3.getUri());
        assertEquals(builder, configuration3.getApacheHttpClientBuilder());

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