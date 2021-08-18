package com.checkout.payments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerSourceTest {

    @Test
    public void missing_id_and_email_should_throw_exception() {
        assertThrows(IllegalArgumentException.class, () -> new CustomerSource(null, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@@", "test@", "test@@test", "test"})
    public void given_invalid_email_should_throw_exception(final String email) {
        assertThrows(IllegalArgumentException.class, () -> new CustomerSource(null, email));
    }

}