package com.checkout;

import org.junit.jupiter.api.Test;

import static com.checkout.PlatformType.DEFAULT;
import static com.checkout.TestHelper.INVALID_DEFAULT_PK;
import static com.checkout.TestHelper.INVALID_DEFAULT_SK;
import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class DefaultStaticKeysSdkCredentialsTest {

    @Test
    void shouldFailCreatingDefaultStaticKeysSdkCredentials_invalidKeys() {
        try {
            new DefaultStaticKeysSdkCredentials(VALID_DEFAULT_SK, INVALID_DEFAULT_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            new DefaultStaticKeysSdkCredentials(INVALID_DEFAULT_SK, VALID_DEFAULT_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void shouldCreateDefaultStaticKeysSdkCredentials() {

        final DefaultStaticKeysSdkCredentials credentials = new DefaultStaticKeysSdkCredentials(VALID_DEFAULT_SK, VALID_DEFAULT_PK);
        assertEquals(DEFAULT, credentials.getPlatformType());
        assertEquals(VALID_DEFAULT_PK, credentials.getPublicKey());
        assertEquals(VALID_DEFAULT_SK, credentials.getSecretKey());

    }

    @Test
    void shouldCreateDefaultStaticKeysSdkCredentialsForProd() {

        final String validDefaultSk = "sk_fde517a8-3f01-41ef-b4bd-4282384b0a64";
        final String validDefaultPk = "pk_fe70ff27-7c32-4ce1-ae90-5691a188ee7b";

        final DefaultStaticKeysSdkCredentials credentials = new DefaultStaticKeysSdkCredentials(validDefaultSk, validDefaultPk);

        assertEquals(DEFAULT, credentials.getPlatformType());
        assertEquals(validDefaultPk, credentials.getPublicKey());
        assertEquals(validDefaultSk, credentials.getSecretKey());

    }

    @Test
    void shouldFailToCreateDefaultStaticKeysSdkCredentialsForProd() {

        final String similarDefaultSk = "sk_tost_m73dzbpy7cf3gfd46xr4yj5xo4e";
        final String similarDefaultPk = "pk_tost_pkhpdtvmkgf7hdnpwnbhw7r2uic";

        try {
            final DefaultStaticKeysSdkCredentials credentials = new DefaultStaticKeysSdkCredentials(VALID_DEFAULT_SK, similarDefaultPk);
            new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            final DefaultStaticKeysSdkCredentials credentials = new DefaultStaticKeysSdkCredentials(similarDefaultSk, VALID_DEFAULT_PK);
            new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void shouldGetAuthorization() {

        final DefaultStaticKeysSdkCredentials credentials = new DefaultStaticKeysSdkCredentials(VALID_DEFAULT_SK, VALID_DEFAULT_PK);

        final SdkAuthorization auth1 = credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY);
        assertNotNull(auth1);
        assertEquals(VALID_DEFAULT_SK, auth1.getAuthorizationHeader());

        final SdkAuthorization auth2 = credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY);
        assertNotNull(auth2);
        assertEquals(VALID_DEFAULT_PK, auth2.getAuthorizationHeader());

    }

    @Test
    void shouldNotGetAuthorization() {

        final DefaultStaticKeysSdkCredentials credentials = new DefaultStaticKeysSdkCredentials(VALID_DEFAULT_SK, VALID_DEFAULT_PK);

        try {
            credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutAuthorizationException);
            assertEquals("Operation requires SECRET_KEY_OR_OAUTH authorization type", e.getMessage());
        }

    }

}