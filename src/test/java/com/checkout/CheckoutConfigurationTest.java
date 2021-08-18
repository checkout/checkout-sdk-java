package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static com.checkout.PlatformType.CLASSIC;
import static com.checkout.PlatformType.FOUR;
import static com.checkout.TestHelper.INVALID_CLASSIC_PK;
import static com.checkout.TestHelper.INVALID_CLASSIC_SK;
import static com.checkout.TestHelper.INVALID_FOUR_PK;
import static com.checkout.TestHelper.INVALID_FOUR_SK;
import static com.checkout.TestHelper.VALID_CLASSIC_PK;
import static com.checkout.TestHelper.VALID_CLASSIC_SK;
import static com.checkout.TestHelper.VALID_FOUR_PK;
import static com.checkout.TestHelper.VALID_FOUR_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class CheckoutConfigurationTest {

    @Test
    public void shouldFailCreatingConfiguration_invalidEnvironment() {
        try {
            new CheckoutConfiguration(VALID_FOUR_PK, VALID_FOUR_SK, (Environment) null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment must be not be null", e.getMessage());
        }
    }

    @Test
    public void shouldFailCreatingConfiguration_invalidKeys() {
        try {
            new CheckoutConfiguration(INVALID_FOUR_PK, VALID_FOUR_SK, Environment.SANDBOX);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            new CheckoutConfiguration(VALID_FOUR_PK, INVALID_FOUR_SK, Environment.SANDBOX);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    public void shouldFailCreatingConfiguration_invalidURI() {
        try {
            new CheckoutConfiguration(VALID_FOUR_PK, VALID_FOUR_SK, (URI) null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("uri must be not be null", e.getMessage());
        }
    }

    @Test
    public void shouldCreateConfiguration() throws URISyntaxException {

        final CheckoutConfiguration checkoutConfiguration = new CheckoutConfiguration(VALID_FOUR_PK, VALID_FOUR_SK, Environment.PRODUCTION);
        assertEquals(FOUR, checkoutConfiguration.getCustomerPlatformType());
        assertEquals(VALID_FOUR_PK, checkoutConfiguration.getPublicKey());
        assertEquals(VALID_FOUR_SK, checkoutConfiguration.getSecretKey());
        assertEquals(Environment.PRODUCTION.getUri(), checkoutConfiguration.getUri());

        final CheckoutConfiguration checkoutConfiguration2 = new CheckoutConfiguration(VALID_FOUR_PK, VALID_FOUR_SK, new URI("https://www.test.checkout.com/"));
        assertEquals(FOUR, checkoutConfiguration2.getCustomerPlatformType());
        assertEquals(VALID_FOUR_PK, checkoutConfiguration2.getPublicKey());
        assertEquals(VALID_FOUR_SK, checkoutConfiguration2.getSecretKey());
        assertEquals("https://www.test.checkout.com/", checkoutConfiguration2.getUri());

    }

    @Test
    public void toDeprecate_shouldFailCreatingConfiguration_classic_invalidURI() {
        try {
            new CheckoutConfiguration(VALID_FOUR_PK, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("uri must be not be blank", e.getMessage());
        }
    }

    @Test
    public void toDeprecate_shouldFailCreatingConfiguration_invalidKeys() {
        try {
            new CheckoutConfiguration(INVALID_CLASSIC_SK, true);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
        try {
            final CheckoutConfiguration configuration = new CheckoutConfiguration(VALID_CLASSIC_SK, true);
            configuration.setPublicKey(INVALID_CLASSIC_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
    }

    @Test
    public void toDeprecate_shouldCreateConfiguration_classic() {

        final CheckoutConfiguration checkoutConfiguration = new CheckoutConfiguration(VALID_CLASSIC_SK, true);
        assertEquals(CLASSIC, checkoutConfiguration.getCustomerPlatformType());
        assertNull(checkoutConfiguration.getPublicKey());
        assertEquals(VALID_CLASSIC_SK, checkoutConfiguration.getSecretKey());
        assertEquals(Environment.SANDBOX.getUri(), checkoutConfiguration.getUri());

        final CheckoutConfiguration checkoutConfiguration2 = new CheckoutConfiguration(VALID_CLASSIC_SK, false, VALID_CLASSIC_PK);
        assertEquals(CLASSIC, checkoutConfiguration2.getCustomerPlatformType());
        assertEquals(VALID_CLASSIC_PK, checkoutConfiguration2.getPublicKey());
        assertEquals(VALID_CLASSIC_SK, checkoutConfiguration2.getSecretKey());
        assertEquals(Environment.PRODUCTION.getUri(), checkoutConfiguration2.getUri());

        final HttpClientBuilder builder = mock(HttpClientBuilder.class);

        final CheckoutConfiguration checkoutConfiguration3 = new CheckoutConfiguration(VALID_CLASSIC_SK, "www.google.com");
        checkoutConfiguration3.setApacheHttpClientBuilder(builder);
        checkoutConfiguration3.setPublicKey(VALID_CLASSIC_PK);

        assertEquals(CLASSIC, checkoutConfiguration3.getCustomerPlatformType());
        assertEquals(VALID_CLASSIC_PK, checkoutConfiguration3.getPublicKey());
        assertEquals(VALID_CLASSIC_SK, checkoutConfiguration3.getSecretKey());
        assertEquals("www.google.com", checkoutConfiguration3.getUri());
        assertEquals(builder, checkoutConfiguration3.getApacheHttpClientBuilder());

    }

}