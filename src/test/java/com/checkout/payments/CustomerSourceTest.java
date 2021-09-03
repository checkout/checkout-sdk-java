package com.checkout.payments;

import com.checkout.CheckoutArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CustomerSourceTest {

    @Test
    void shouldThrowExceptionWhenIdAndEmailAreNotProvided() {
        try {
            new CustomerSource(null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("either the customer id or email is required", e.getMessage());
        }
    }

}