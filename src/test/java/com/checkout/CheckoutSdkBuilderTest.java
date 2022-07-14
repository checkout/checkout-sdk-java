package com.checkout;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static com.checkout.TestHelper.INVALID_DEFAULT_PK;
import static com.checkout.TestHelper.INVALID_DEFAULT_SK;
import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CheckoutSdkBuilderTest {

    @Test
    void shouldCreateStaticKeysCheckoutSdks() {

        final CheckoutApi checkoutApi1 = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi1);

        final CheckoutApi checkoutApi2 = new CheckoutSdkBuilder().staticKeys()
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi2);

    }

    @Test
    void shouldCreateCheckoutAndInitOAuthSdk() throws URISyntaxException {

        try {
            new CheckoutSdkBuilder().oAuth()
                    .clientCredentials(new URI("test"), "client_id", "client_secret")
                    .scopes(OAuthScope.GATEWAY)
                    .environment(Environment.SANDBOX)
                    .build();
            fail();
        } catch (final CheckoutException e) {
            assertEquals("OAuth client_credentials authentication failed", e.getMessage());
        }

    }

    @Test
    void shouldFailToCreateCheckoutSdks() {

        try {
            new CheckoutSdkBuilder().staticKeys()
                    .publicKey(INVALID_DEFAULT_PK)
                    .secretKey(VALID_DEFAULT_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }

        try {
            new CheckoutSdkBuilder().staticKeys()
                    .publicKey(VALID_DEFAULT_PK)
                    .secretKey(INVALID_DEFAULT_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }

        try {
            new CheckoutSdkBuilder().staticKeys()
                    .publicKey(VALID_DEFAULT_PK)
                    .secretKey(VALID_DEFAULT_SK)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment must be specified", e.getMessage());
        }

    }

}