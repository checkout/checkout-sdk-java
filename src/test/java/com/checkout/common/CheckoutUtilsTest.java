package com.checkout.common;

import com.checkout.CheckoutArgumentException;
import org.junit.jupiter.api.Test;

import static com.checkout.common.CheckoutUtils.validateParams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CheckoutUtilsTest {

    @Test
    public void shouldRequireNonBlank() {

        testNoExceptionIsThrownRequiresNonBlank("name1", "john");
        testExceptionIsThrownRequiresNonBlank("age1", "");
        testExceptionIsThrownRequiresNonBlank("age2", "  ");

    }

    @Test
    public void shouldRequireNonNull() {

        testNoExceptionIsThrownRequiresNonNull("name1", "john");
        testNoExceptionIsThrownRequiresNonNull("name2", new Object());
        testNoExceptionIsThrownRequiresNonNull("name3", 20L);
        testExceptionIsThrownRequiresNonNull("name4", null);

    }

    @Test
    public void shouldThrowOnNullOrEmptyProperty() {

        testExceptionIsThrownOnInvalidProperty(null);
        testExceptionIsThrownOnInvalidProperty("");
        testExceptionIsThrownOnInvalidProperty(" ");

    }

    private void testNoExceptionIsThrownRequiresNonBlank(final String property, final String content) {
        try {
            validateParams(property, content);
            validateParams(property, content, "test", new Object());
            validateParams("test1", new Object(), property, content, "test2", new Object());
            validateParams("test1", new Object(), "test2", new Object(), property, content, "test3", new Object());
        } catch (final Exception e) {
            fail();
        }
    }

    private void testExceptionIsThrownRequiresNonBlank(final String property, final String content) {
        try {
            validateParams(property, content);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(property + " cannot be blank", e.getMessage());
        }
        try {
            validateParams(property, content, "test", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(property + " cannot be blank", e.getMessage());
        }
        try {
            validateParams("test1", new Object(), property, content, "test2", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(property + " cannot be blank", e.getMessage());
        }
        try {
            validateParams("test1", new Object(), "test2", new Object(), property, content, "test3", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(property + " cannot be blank", e.getMessage());
        }
    }

    private void testExceptionIsThrownRequiresNonNull(final String property, final String content) {
        try {
            validateParams(property, content);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(property + " cannot be null", e.getMessage());
        }
        try {
            validateParams(property, content, "test", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(property + " cannot be null", e.getMessage());
        }
        try {
            validateParams("test1", new Object(), property, content, "test2", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(property + " cannot be null", e.getMessage());
        }
        try {
            validateParams("test1", new Object(), "test2", new Object(), property, content, "test3", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(property + " cannot be null", e.getMessage());
        }
    }

    private void testNoExceptionIsThrownRequiresNonNull(final String property, final Object content) {
        try {
            validateParams(property, content);
            validateParams(property, content, "test", new Object());
            validateParams("test1", new Object(), property, content, "test2", new Object());
            validateParams("test1", new Object(), "test2", new Object(), property, content, "test3", new Object());
        } catch (final Exception e) {
            fail();
        }
    }

    private void testExceptionIsThrownOnInvalidProperty(final String property) {
        try {
            validateParams(property, new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalStateException);
            assertEquals("invalid validation key", e.getMessage());
        }
        try {
            validateParams(property, new Object(), "test", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalStateException);
            assertEquals("invalid validation key", e.getMessage());
        }
        try {
            validateParams("test1", new Object(), property, new Object(), "test2", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalStateException);
            assertEquals("invalid validation key", e.getMessage());
        }
        try {
            validateParams("test1", new Object(), "test2", new Object(), property, new Object(), "test3", new Object());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalStateException);
            assertEquals("invalid validation key", e.getMessage());
        }
    }

}