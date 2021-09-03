package com.checkout.payments;


import com.checkout.CheckoutArgumentException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenSourceTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "", "  "})
    void shouldThrowExceptionOnInvalidInput(final String input) {
        assertThrows(CheckoutArgumentException.class, () -> new TokenSource(input));
    }

}
