package com.checkout;

import org.junit.jupiter.api.Test;

import static com.checkout.PlatformType.FOUR;
import static com.checkout.TestHelper.INVALID_FOUR_PK;
import static com.checkout.TestHelper.INVALID_FOUR_SK;
import static com.checkout.TestHelper.VALID_FOUR_PK;
import static com.checkout.TestHelper.VALID_FOUR_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class FourStaticKeysSdkCredentialsTest {

    private static final String BEARER = "Bearer ";

    @Test
    void shouldFailCreatingFourStaticKeysSdkCredentials_invalidKeys() {
        try {
            new FourStaticKeysSdkCredentials(VALID_FOUR_SK, INVALID_FOUR_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            new FourStaticKeysSdkCredentials(INVALID_FOUR_SK, VALID_FOUR_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void four_shouldCreateFourStaticKeysSdkCredentials() {

        final FourStaticKeysSdkCredentials credentials = new FourStaticKeysSdkCredentials(VALID_FOUR_SK, VALID_FOUR_PK);
        assertEquals(FOUR, credentials.getPlatformType());
        assertEquals(VALID_FOUR_PK, credentials.getPublicKey());
        assertEquals(VALID_FOUR_SK, credentials.getSecretKey());

    }

    @Test
    void shouldCreateFourStaticKeysSdkCredentialsForProd() {

        final String validFourSk = "sk_m73dzbpy7cf3gfd46xr4yj5xo4e";
        final String validFourPk = "pk_pkhpdtvmkgf7hdnpwnbhw7r2uic";

        final FourStaticKeysSdkCredentials credentials = new FourStaticKeysSdkCredentials(validFourSk, validFourPk);

        assertEquals(FOUR, credentials.getPlatformType());
        assertEquals(validFourPk, credentials.getPublicKey());
        assertEquals(validFourSk, credentials.getSecretKey());

    }

    @Test
    void shouldFailToCreateFourStaticKeysSdkCredentialsForProd() {

        final String similarFourSk = "sk_tost_m73dzbpy7cf3gfd46xr4yj5xo4e";
        final String similarFourPk = "pk_tost_pkhpdtvmkgf7hdnpwnbhw7r2uic";

        try {
            final FourStaticKeysSdkCredentials credentials = new FourStaticKeysSdkCredentials(VALID_FOUR_SK, similarFourPk);
            new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            final FourStaticKeysSdkCredentials credentials = new FourStaticKeysSdkCredentials(similarFourSk, VALID_FOUR_PK);
            new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void shouldGetAuthorization() {

        final FourStaticKeysSdkCredentials credentials = new FourStaticKeysSdkCredentials(VALID_FOUR_SK, VALID_FOUR_PK);

        final SdkAuthorization auth1 = credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY);
        assertNotNull(auth1);
        assertEquals(BEARER + VALID_FOUR_SK, auth1.getAuthorizationHeader());

        final SdkAuthorization auth2 = credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
        assertNotNull(auth2);
        assertEquals(BEARER + VALID_FOUR_SK, auth2.getAuthorizationHeader());

        final SdkAuthorization auth3 = credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY);
        assertNotNull(auth3);
        assertEquals(BEARER + VALID_FOUR_PK, auth3.getAuthorizationHeader());

        final SdkAuthorization auth4 = credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY_OR_OAUTH);
        assertNotNull(auth4);
        assertEquals(BEARER + VALID_FOUR_PK, auth4.getAuthorizationHeader());

    }

}