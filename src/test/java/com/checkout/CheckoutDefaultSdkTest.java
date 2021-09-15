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

class CheckoutDefaultSdkTest {

    @Test
    void shouldCreateCheckoutSdks() throws URISyntaxException {

        final CheckoutApi checkoutApi1 = new CheckoutDefaultSdk()
                .staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi1.tokensClient());

        final CheckoutApi checkoutApi2 = new CheckoutDefaultSdk().staticKeys()
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi2.tokensClient());

        final CheckoutApi checkoutApi3 = new CheckoutDefaultSdk().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .uri(new URI("https://test.checkout.com"))
                .build();

        assertNotNull(checkoutApi3.tokensClient());

    }

    @Test
    void shouldFailToCreateCheckoutSdks() {

        try {
            new CheckoutDefaultSdk().staticKeys()
                    .publicKey(INVALID_DEFAULT_PK)
                    .secretKey(VALID_DEFAULT_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }

        try {
            new CheckoutDefaultSdk().staticKeys()
                    .publicKey(VALID_DEFAULT_PK)
                    .secretKey(INVALID_DEFAULT_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }

        try {
            new CheckoutDefaultSdk().staticKeys()
                    .publicKey(VALID_DEFAULT_PK)
                    .secretKey(VALID_DEFAULT_SK)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment or URI must be specified", e.getMessage());
        }

    }

}
