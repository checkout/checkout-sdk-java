package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static com.checkout.PlatformType.DEFAULT;
import static com.checkout.PlatformType.FOUR;
import static com.checkout.TestHelper.INVALID_DEFAULT_PK;
import static com.checkout.TestHelper.INVALID_DEFAULT_SK;
import static com.checkout.TestHelper.INVALID_FOUR_PK;
import static com.checkout.TestHelper.INVALID_FOUR_SK;
import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
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
    public void toDeprecate_shouldFailCreatingConfiguration_default_invalidURI() {
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
            new CheckoutConfiguration(INVALID_DEFAULT_SK, true);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
        try {
            final CheckoutConfiguration configuration = new CheckoutConfiguration(VALID_DEFAULT_SK, true);
            configuration.setPublicKey(INVALID_DEFAULT_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
    }

    @Test
    public void toDeprecate_shouldCreateConfiguration_default() {

        final CheckoutConfiguration checkoutConfiguration = new CheckoutConfiguration(VALID_DEFAULT_SK, true);
        assertEquals(DEFAULT, checkoutConfiguration.getCustomerPlatformType());
        assertNull(checkoutConfiguration.getPublicKey());
        assertEquals(VALID_DEFAULT_SK, checkoutConfiguration.getSecretKey());
        assertEquals(Environment.SANDBOX.getUri(), checkoutConfiguration.getUri());

        final CheckoutConfiguration checkoutConfiguration2 = new CheckoutConfiguration(VALID_DEFAULT_SK, false, VALID_DEFAULT_PK);
        assertEquals(DEFAULT, checkoutConfiguration2.getCustomerPlatformType());
        assertEquals(VALID_DEFAULT_PK, checkoutConfiguration2.getPublicKey());
        assertEquals(VALID_DEFAULT_SK, checkoutConfiguration2.getSecretKey());
        assertEquals(Environment.PRODUCTION.getUri(), checkoutConfiguration2.getUri());

        final HttpClientBuilder builder = mock(HttpClientBuilder.class);

        final CheckoutConfiguration checkoutConfiguration3 = new CheckoutConfiguration(VALID_DEFAULT_SK, "www.google.com");
        checkoutConfiguration3.setApacheHttpClientBuilder(builder);
        checkoutConfiguration3.setPublicKey(VALID_DEFAULT_PK);

        assertEquals(DEFAULT, checkoutConfiguration3.getCustomerPlatformType());
        assertEquals(VALID_DEFAULT_PK, checkoutConfiguration3.getPublicKey());
        assertEquals(VALID_DEFAULT_SK, checkoutConfiguration3.getSecretKey());
        assertEquals("www.google.com", checkoutConfiguration3.getUri());
        assertEquals(builder, checkoutConfiguration3.getApacheHttpClientBuilder());

    }

    @Test
    public void shouldCreateFourConfigurationForProd() throws URISyntaxException {

        final String validFourSk = "sk_m73dzbpy7cf3gfd46xr4yj5xo4e";
        final String validFourPk = "pk_pkhpdtvmkgf7hdnpwnbhw7r2uic";

        final CheckoutConfiguration checkoutConfiguration = new CheckoutConfiguration(validFourPk, validFourSk, Environment.PRODUCTION);
        assertEquals(FOUR, checkoutConfiguration.getCustomerPlatformType());
        assertEquals(validFourPk, checkoutConfiguration.getPublicKey());
        assertEquals(validFourSk, checkoutConfiguration.getSecretKey());
        assertEquals(Environment.PRODUCTION.getUri(), checkoutConfiguration.getUri());

        final CheckoutConfiguration checkoutConfiguration2 = new CheckoutConfiguration(validFourPk, validFourSk, new URI("https://www.test.checkout.com/"));
        assertEquals(FOUR, checkoutConfiguration2.getCustomerPlatformType());
        assertEquals(validFourPk, checkoutConfiguration2.getPublicKey());
        assertEquals(validFourSk, checkoutConfiguration2.getSecretKey());
        assertEquals("https://www.test.checkout.com/", checkoutConfiguration2.getUri());

    }

    @Test
    public void shouldCreateDefaultConfigurationForProd() {

        final String validDefaultSk = "sk_fde517a8-3f01-41ef-b4bd-4282384b0a64";
        final String validDefaultPk = "pk_fe70ff27-7c32-4ce1-ae90-5691a188ee7b";

        final CheckoutConfiguration checkoutConfiguration = new CheckoutConfiguration(validDefaultSk, true);
        assertEquals(DEFAULT, checkoutConfiguration.getCustomerPlatformType());
        assertNull(checkoutConfiguration.getPublicKey());
        assertEquals(validDefaultSk, checkoutConfiguration.getSecretKey());
        assertEquals(Environment.SANDBOX.getUri(), checkoutConfiguration.getUri());

        final CheckoutConfiguration checkoutConfiguration2 = new CheckoutConfiguration(validDefaultSk, false, validDefaultPk);
        assertEquals(DEFAULT, checkoutConfiguration2.getCustomerPlatformType());
        assertEquals(validDefaultPk, checkoutConfiguration2.getPublicKey());
        assertEquals(validDefaultSk, checkoutConfiguration2.getSecretKey());
        assertEquals(Environment.PRODUCTION.getUri(), checkoutConfiguration2.getUri());
    }

    @Test
    public void shouldFailCreatingConfigurationInvalidKeysWithSamePatternDefault() {
        final String similarDefaultSk = "sk_tost_fde517a8-3f01-41ef-b4bd-4282384b0a64";
        final String similarDefaultPk = "pk_tost_fe70ff27-7c32-4ce1-ae90-5691a188ee7b";
        try {
            new CheckoutConfiguration(similarDefaultSk, true);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
        try {
            final CheckoutConfiguration configuration = new CheckoutConfiguration(VALID_DEFAULT_SK, true);
            configuration.setPublicKey(similarDefaultPk);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
    }

    @Test
    public void shouldFailCreatingConfigurationInvalidKeysWithSamePatternFour() {
        final String similarFourSk = "sk_tost_m73dzbpy7cf3gfd46xr4yj5xo4e";
        final String similarFourPk = "pk_tost_pkhpdtvmkgf7hdnpwnbhw7r2uic";
        try {
            new CheckoutConfiguration(similarFourPk, VALID_FOUR_SK, Environment.SANDBOX);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            new CheckoutConfiguration(VALID_FOUR_PK, similarFourSk, Environment.SANDBOX);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

}