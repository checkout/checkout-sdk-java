package com.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class AbstractClientBuildPathTest {

    private static Stream<Arguments> encodedValues() {
        return Stream.of(
                Arguments.of("pay_123", "payments/pay_123"),
                Arguments.of("pay_1?x=1", "payments/pay_1%3Fx%3D1"),
                Arguments.of("pay_1#x", "payments/pay_1%23x"),
                Arguments.of("pay_1%2Frefunds", "payments/pay_1%252Frefunds"),
                Arguments.of("my secret", "payments/my%20secret"),
                Arguments.of("clé", "payments/cl%C3%A9"),
                Arguments.of("a.b-c_d~e", "payments/a.b-c_d~e"),
                Arguments.of("...", "payments/..."),
                Arguments.of("\ud83d\ude00", "payments/%F0%9F%98%80")
        );
    }

    @ParameterizedTest
    @MethodSource("encodedValues")
    void shouldEncodeEachPathParameterAsOneSegment(final String value, final String expected) {
        assertEquals(expected, AbstractClient.buildPath("payments", value));
    }

    @Test
    void shouldKeepPlainPathsUnchanged() {
        assertEquals("disputes/dsp_123/evidence", AbstractClient.buildPath("disputes", "dsp_123", "evidence"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", ".", "..", "\ud800", "a\udc00b", "\ud83d",
            "pay_1/refunds", "../payments/pay_1", "pay_1\\refunds", "pay_1\r\nX: y", "pay_1\u0000",
            "pay_1\t", "pay_1\u001f", "pay_1\u007f"})
    void shouldRefuseInvalidPathParameters(final String value) {
        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class,
                () -> AbstractClient.buildPath("payments", value, "refunds"));
        assertEquals("Invalid path parameter: it must be valid text, not null, empty, '.' or '..', "
                + "and must not contain '/', '\\' or ASCII control characters", exception.getMessage());
    }

    @Test
    void shouldKeepCharactersOutsideTheForbiddenSetEncoded() {
        assertEquals("payments/pay_1%C2%80%20%25", AbstractClient.buildPath("payments", "pay_1\u0080 %"));
    }

}
