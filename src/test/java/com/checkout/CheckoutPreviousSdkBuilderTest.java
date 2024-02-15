package com.checkout;

import com.checkout.previous.CheckoutApi;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.INVALID_PREVIOUS_PK;
import static com.checkout.TestHelper.INVALID_PREVIOUS_SK;
import static com.checkout.TestHelper.VALID_PREVIOUS_PK;
import static com.checkout.TestHelper.VALID_PREVIOUS_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutPreviousSdkBuilderTest {

    @Test
    void shouldCreateCheckoutSdks() {

        final CheckoutApi checkoutApi1 = new CheckoutPreviousSdkBuilder()
                .staticKeys()
                .publicKey(VALID_PREVIOUS_PK)
                .secretKey(VALID_PREVIOUS_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi1.tokensClient());

        final CheckoutApi checkoutApi2 = new CheckoutPreviousSdkBuilder().staticKeys()
                .secretKey(VALID_PREVIOUS_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi2.tokensClient());

    }

    @Test
    void shouldCreateCheckoutWithSubdomainSdks() {

        final CheckoutApi checkoutApi1 = new CheckoutPreviousSdkBuilder()
                .staticKeys()
                .publicKey(VALID_PREVIOUS_PK)
                .secretKey(VALID_PREVIOUS_SK)
                .environment(Environment.SANDBOX)
                .environmentSubdomain("123dmain")
                .build();

        assertNotNull(checkoutApi1.tokensClient());

        final CheckoutApi checkoutApi2 = new CheckoutPreviousSdkBuilder().staticKeys()
                .secretKey(VALID_PREVIOUS_SK)
                .environment(Environment.SANDBOX)
                .environmentSubdomain("123dmain")
                .build();

        assertNotNull(checkoutApi2.tokensClient());

    }

    @Test
    void shouldFailToCreateCheckoutSdks() {

        try {
            new CheckoutPreviousSdkBuilder().staticKeys()
                    .publicKey(INVALID_PREVIOUS_PK)
                    .secretKey(VALID_PREVIOUS_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid public key", e.getMessage());
        }

        try {
            new CheckoutPreviousSdkBuilder().staticKeys()
                    .publicKey(VALID_PREVIOUS_PK)
                    .secretKey(INVALID_PREVIOUS_SK)
                    .environment(Environment.SANDBOX)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("invalid secret key", e.getMessage());
        }

        try {
            new CheckoutPreviousSdkBuilder().staticKeys()
                    .publicKey(VALID_PREVIOUS_PK)
                    .secretKey(VALID_PREVIOUS_SK)
                    .build();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment must be specified", e.getMessage());
        }

    }

}
