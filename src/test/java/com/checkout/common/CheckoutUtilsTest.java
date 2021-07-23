package com.checkout.common;

import com.checkout.CheckoutArgumentException;
import org.junit.Test;

import static com.checkout.common.CheckoutUtils.requiresNonBlank;
import static com.checkout.common.CheckoutUtils.requiresNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    private void testNoExceptionIsThrownRequiresNonBlank(String property, String content) {
        try {
            requiresNonBlank(property, content);
        } catch (Exception e) {
            fail();
        }
    }

    private void testExceptionIsThrownRequiresNonBlank(String property, String content) {
        try {
            requiresNonBlank(property, content);
            fail();
        } catch (CheckoutArgumentException e) {
            assertEquals(property + " must be not be blank", e.getMessage());
        }
    }

    private void testNoExceptionIsThrownRequiresNonNull(String property, Object content) {
        try {
            requiresNonNull(property, content);
        } catch (Exception e) {
            fail();
        }
    }

    private void testExceptionIsThrownRequiresNonNull(String property, Object content) {
        try {
            requiresNonNull(property, content);
            fail();
        } catch (CheckoutArgumentException e) {
            assertEquals(property + " must be not be null", e.getMessage());
        }
    }

}