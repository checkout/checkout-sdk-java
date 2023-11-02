package com.checkout;

import static com.checkout.PlatformType.DEFAULT;
import static com.checkout.TestHelper.INVALID_DEFAULT_PK;
import static com.checkout.TestHelper.INVALID_DEFAULT_SK;
import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class StaticKeysSdkCredentialsTest {

    private static final String BEARER = "Bearer ";

    @Test
    void shouldFailCreatingStaticKeysSdkCredentials_invalidKeys() {
        try {
            new StaticKeysSdkCredentials(VALID_DEFAULT_SK, INVALID_DEFAULT_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            new StaticKeysSdkCredentials(INVALID_DEFAULT_SK, VALID_DEFAULT_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void shouldCreateStaticKeysSdkCredentials() {

        final StaticKeysSdkCredentials credentials = new StaticKeysSdkCredentials(VALID_DEFAULT_SK, VALID_DEFAULT_PK);
        assertEquals(DEFAULT, credentials.getPlatformType());
        assertEquals(VALID_DEFAULT_PK, credentials.getPublicKey());
        assertEquals(VALID_DEFAULT_SK, credentials.getSecretKey());

    }

    @Test
    void shouldCreateStaticKeysSdkCredentialsForProd() {

        final String validDefaultSk = "sk_m73dzbpy7cf3gfd46xr4yj5xo4e";
        final String validDefaultPk = "pk_pkhpdtvmkgf7hdnpwnbhw7r2uic";

        final StaticKeysSdkCredentials credentials = new StaticKeysSdkCredentials(validDefaultSk, validDefaultPk);

        assertEquals(DEFAULT, credentials.getPlatformType());
        assertEquals(validDefaultPk, credentials.getPublicKey());
        assertEquals(validDefaultSk, credentials.getSecretKey());

    }

    @Test
    void shouldFailToCreateStaticKeysSdkCredentialsForProd() {

        final String similarDefaultSk = "sk_tost_m73dzbpy7cf3gfd46xr4yj5xo4e";
        final String similarDefaultPk = "pk_tost_pkhpdtvmkgf7hdnpwnbhw7r2uic";

        try {
            final StaticKeysSdkCredentials credentials = new StaticKeysSdkCredentials(VALID_DEFAULT_SK, similarDefaultPk);
            new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            final StaticKeysSdkCredentials credentials = new StaticKeysSdkCredentials(similarDefaultSk, VALID_DEFAULT_PK);
            new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void shouldGetAuthorization() {

        final StaticKeysSdkCredentials credentials = new StaticKeysSdkCredentials(VALID_DEFAULT_SK, VALID_DEFAULT_PK);

        final SdkAuthorization auth1 = credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY);
        assertNotNull(auth1);
        assertEquals(BEARER + VALID_DEFAULT_SK, auth1.getAuthorizationHeader());

        final SdkAuthorization auth2 = credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
        assertNotNull(auth2);
        assertEquals(BEARER + VALID_DEFAULT_SK, auth2.getAuthorizationHeader());

        final SdkAuthorization auth3 = credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY);
        assertNotNull(auth3);
        assertEquals(BEARER + VALID_DEFAULT_PK, auth3.getAuthorizationHeader());

        final SdkAuthorization auth4 = credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY_OR_OAUTH);
        assertNotNull(auth4);
        assertEquals(BEARER + VALID_DEFAULT_PK, auth4.getAuthorizationHeader());

    }

}