package com.checkout;

import org.junit.jupiter.api.Test;

import static com.checkout.PlatformType.PREVIOUS;
import static com.checkout.TestHelper.INVALID_PREVIOUS_PK;
import static com.checkout.TestHelper.INVALID_PREVIOUS_SK;
import static com.checkout.TestHelper.VALID_PREVIOUS_PK;
import static com.checkout.TestHelper.VALID_PREVIOUS_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class PreviousStaticKeysSdkCredentialsTest {

    @Test
    void shouldFailCreatingPreviousStaticKeysSdkCredentials_invalidKeys() {
        try {
            new PreviousStaticKeysSdkCredentials(VALID_PREVIOUS_SK, INVALID_PREVIOUS_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            new PreviousStaticKeysSdkCredentials(INVALID_PREVIOUS_SK, VALID_PREVIOUS_PK);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void shouldCreatePreviousStaticKeysSdkCredentials() {

        final PreviousStaticKeysSdkCredentials credentials = new PreviousStaticKeysSdkCredentials(VALID_PREVIOUS_SK, VALID_PREVIOUS_PK);
        assertEquals(PREVIOUS, credentials.getPlatformType());
        assertEquals(VALID_PREVIOUS_PK, credentials.getPublicKey());
        assertEquals(VALID_PREVIOUS_SK, credentials.getSecretKey());

    }

    @Test
    void shouldCreatePreviousStaticKeysSdkCredentialsForProd() {

        final String validPreviousSk = "sk_fde517a8-3f01-41ef-b4bd-4282384b0a64";
        final String validPreviousPk = "pk_fe70ff27-7c32-4ce1-ae90-5691a188ee7b";

        final PreviousStaticKeysSdkCredentials credentials = new PreviousStaticKeysSdkCredentials(validPreviousSk, validPreviousPk);

        assertEquals(PREVIOUS, credentials.getPlatformType());
        assertEquals(validPreviousPk, credentials.getPublicKey());
        assertEquals(validPreviousSk, credentials.getSecretKey());

    }

    @Test
    void shouldFailToCreatePreviousStaticKeysSdkCredentialsForProd() {

        final String similarDefaultSk = "sk_tost_m73dzbpy7cf3gfd46xr4yj5xo4e";
        final String similarDefaultPk = "pk_tost_pkhpdtvmkgf7hdnpwnbhw7r2uic";

        try {
            final PreviousStaticKeysSdkCredentials credentials = new PreviousStaticKeysSdkCredentials(VALID_PREVIOUS_SK, similarDefaultPk);
            new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }
        try {
            final PreviousStaticKeysSdkCredentials credentials = new PreviousStaticKeysSdkCredentials(similarDefaultSk, VALID_PREVIOUS_PK);
            new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }
    }

    @Test
    void shouldGetAuthorization() {

        final PreviousStaticKeysSdkCredentials credentials = new PreviousStaticKeysSdkCredentials(VALID_PREVIOUS_SK, VALID_PREVIOUS_PK);

        final SdkAuthorization auth1 = credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY);
        assertNotNull(auth1);
        assertEquals(VALID_PREVIOUS_SK, auth1.getAuthorizationHeader());

        final SdkAuthorization auth2 = credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY);
        assertNotNull(auth2);
        assertEquals(VALID_PREVIOUS_PK, auth2.getAuthorizationHeader());

    }

    @Test
    void shouldNotGetAuthorization() {

        final PreviousStaticKeysSdkCredentials credentials = new PreviousStaticKeysSdkCredentials(VALID_PREVIOUS_SK, VALID_PREVIOUS_PK);

        try {
            credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutAuthorizationException);
            assertEquals("Operation requires SECRET_KEY_OR_OAUTH authorization type", e.getMessage());
        }

    }

}