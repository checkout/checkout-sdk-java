package com.checkout.payments;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenSourceTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "", "  "})
    public void given_token_invalid_should_throw_exception(final String input) {
        assertThrows(IllegalArgumentException.class, () -> new TokenSource(input));
    }

}