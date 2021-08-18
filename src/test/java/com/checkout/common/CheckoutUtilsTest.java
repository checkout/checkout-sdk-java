package com.checkout.common;

import com.checkout.CheckoutArgumentException;
import org.junit.jupiter.api.Test;

import static com.checkout.common.CheckoutUtils.requiresNonBlank;
import static com.checkout.common.CheckoutUtils.requiresNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CheckoutUtilsTest {

    @Test
    public void testRequiresNonBlank() {

        testNoExceptionIsThrownRequiresNonBlank("name", "john");
        testExceptionIsThrownRequiresNonBlank("name", null);
        testExceptionIsThrownRequiresNonBlank("age", "");
        testExceptionIsThrownRequiresNonBlank("age", "  ");

    }

    @Test
    public void testRequiresNonNull() {

        testNoExceptionIsThrownRequiresNonNull("name", "john");
        testNoExceptionIsThrownRequiresNonNull("name", new Object());
        testNoExceptionIsThrownRequiresNonNull("name", 20L);
        testExceptionIsThrownRequiresNonNull("name", null);

    }

    private void testNoExceptionIsThrownRequiresNonBlank(final String property, final String content) {
        try {
            requiresNonBlank(property, content);
        } catch (final Exception e) {
            fail();
        }
    }

    private void testExceptionIsThrownRequiresNonBlank(final String property, final String content) {
        try {
            requiresNonBlank(property, content);
            fail();
        } catch (final CheckoutArgumentException e) {
            assertEquals(property + " must be not be blank", e.getMessage());
        }
    }

    private void testNoExceptionIsThrownRequiresNonNull(final String property, final Object content) {
        try {
            requiresNonNull(property, content);
        } catch (final Exception e) {
            fail();
        }
    }

    private void testExceptionIsThrownRequiresNonNull(final String property, final Object content) {
        try {
            requiresNonNull(property, content);
            fail();
        } catch (final CheckoutArgumentException e) {
            assertEquals(property + " must be not be null", e.getMessage());
        }
    }

}