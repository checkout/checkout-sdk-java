package com.checkout.sessions;

import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutAuthorizationException;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import org.junit.jupiter.api.Test;

import static com.checkout.PlatformType.CUSTOM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SessionSecretSdkCredentialsTest {

    @Test
    void shouldFailCreatingSessionSecretSdkCredentials_invalidSecret() {
        try {
            new SessionSecretSdkCredentials(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("secret cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateSessionSecretSdkCredentials() {

        final SessionSecretSdkCredentials credentials = new SessionSecretSdkCredentials("test");
        assertEquals(CUSTOM, credentials.getPlatformType());
        assertEquals("test", credentials.getSecret());

    }

    @Test
    void shouldGetAuthorization() {

        final SessionSecretSdkCredentials credentials = new SessionSecretSdkCredentials("test");

        final SdkAuthorization auth = credentials.getAuthorization(SdkAuthorizationType.CUSTOM);
        assertNotNull(auth);
        assertEquals("test", auth.getAuthorizationHeader());

    }

    @Test
    void shouldNotGetAuthorization() {

        final SessionSecretSdkCredentials credentials = new SessionSecretSdkCredentials("test");

        try {
            credentials.getAuthorization(SdkAuthorizationType.OAUTH);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutAuthorizationException);
            assertEquals("Operation does not support OAUTH authorization type", e.getMessage());
        }

    }

}