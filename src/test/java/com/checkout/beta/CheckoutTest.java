package com.checkout.beta;

import com.checkout.CheckoutArgumentException;
import com.checkout.Environment;
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

class CheckoutTest {

    @Test
    void shouldCreateCheckoutSdks() throws URISyntaxException {

        final CheckoutApi checkoutApi1 = Checkout.staticKeys()
                .publicKey(VALID_FOUR_PK)
                .secretKey(VALID_FOUR_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi1.tokensClient());

        final CheckoutApi checkoutApi2 = Checkout.staticKeys()
                .secretKey(VALID_FOUR_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi2.tokensClient());

        final CheckoutApi checkoutApi3 = Checkout.staticKeys()
                .secretKey(VALID_FOUR_SK)
                .environment("sandbox")
                .build();

        assertNotNull(checkoutApi3.tokensClient());

        final CheckoutApi checkoutApi4 = Checkout.staticKeys()
                .publicKey(VALID_FOUR_PK)
                .secretKey(VALID_FOUR_SK)
                .uri(new URI("https://test.checkout.com"))
                .build();

        assertNotNull(checkoutApi4.tokensClient());

    }

    @Test
    void shouldFailToCreateCheckoutSdks() {

        try {
            Checkout.staticKeys()
                    .publicKey(INVALID_FOUR_PK)
                    .secretKey(VALID_FOUR_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }

        try {
            Checkout.staticKeys()
                    .publicKey(VALID_FOUR_PK)
                    .secretKey(INVALID_FOUR_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }

        try {
            Checkout.staticKeys()
                    .publicKey(VALID_FOUR_PK)
                    .secretKey(VALID_FOUR_SK)
                    .environment("local")
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid environment property", e.getMessage());
        }

        try {
            Checkout.staticKeys()
                    .publicKey(VALID_FOUR_PK)
                    .secretKey(VALID_FOUR_SK)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment or URI must be specified", e.getMessage());
        }

    }

}