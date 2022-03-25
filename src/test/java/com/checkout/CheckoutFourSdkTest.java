package com.checkout;

import com.checkout.four.CheckoutApi;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static com.checkout.TestHelper.INVALID_FOUR_PK;
import static com.checkout.TestHelper.INVALID_FOUR_SK;
import static com.checkout.TestHelper.VALID_FOUR_PK;
import static com.checkout.TestHelper.VALID_FOUR_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CheckoutFourSdkTest {

    @Test
    void shouldCreateStaticKeysCheckoutSdks() throws URISyntaxException {

        final CheckoutApi checkoutApi1 = new CheckoutFourSdk().staticKeys()
                .publicKey(VALID_FOUR_PK)
                .secretKey(VALID_FOUR_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi1);

        final CheckoutApi checkoutApi2 = new CheckoutFourSdk().staticKeys()
                .secretKey(VALID_FOUR_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi2);

        final CheckoutApi checkoutApi3 = new CheckoutFourSdk().staticKeys()
                .publicKey(VALID_FOUR_PK)
                .secretKey(VALID_FOUR_SK)
                .uri(new URI("https://test.checkout.com"))
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi3);

    }

    @Test
    void shouldCreateCheckoutAndInitOAuthSdk() throws URISyntaxException {

        try {
            new CheckoutFourSdk().oAuth()
                    .clientCredentials(new URI("test"), "client_id", "client_secret")
                    .scopes(FourOAuthScope.GATEWAY)
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
            new CheckoutFourSdk().staticKeys()
                    .publicKey(INVALID_FOUR_PK)
                    .secretKey(VALID_FOUR_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }

        try {
            new CheckoutFourSdk().staticKeys()
                    .publicKey(VALID_FOUR_PK)
                    .secretKey(INVALID_FOUR_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }

        try {
            new CheckoutFourSdk().staticKeys()
                    .publicKey(VALID_FOUR_PK)
                    .secretKey(VALID_FOUR_SK)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment must be specified", e.getMessage());
        }

    }

}