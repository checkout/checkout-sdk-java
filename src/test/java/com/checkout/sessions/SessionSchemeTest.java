package com.checkout.sessions;

import com.checkout.GsonSerializer;
import com.checkout.Serializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SessionSchemeTest {

    private final Serializer serializer = new GsonSerializer();

    private static Stream<Arguments> sessionSchemes() {
        return Stream.of(
                Arguments.of(SessionScheme.VISA, "\"visa\""),
                Arguments.of(SessionScheme.MASTERCARD, "\"mastercard\""),
                Arguments.of(SessionScheme.JCB, "\"jcb\""),
                Arguments.of(SessionScheme.AMEX, "\"amex\""),
                Arguments.of(SessionScheme.DINERS, "\"diners\""),
                Arguments.of(SessionScheme.CARTES_BANCAIRES, "\"cartes_bancaires\""),
                Arguments.of(SessionScheme.DISCOVER, "\"discover\""),
                Arguments.of(SessionScheme.UPI, "\"upi\"")
        );
    }

    @ParameterizedTest
    @MethodSource("sessionSchemes")
    void shouldSerializeSessionSchemeToSnakeCase(final SessionScheme value, final String expectedJson) {
        assertEquals(expectedJson, serializer.toJson(value));
    }

    @ParameterizedTest
    @MethodSource("sessionSchemes")
    void shouldDeserializeSessionSchemeFromSnakeCase(final SessionScheme expected, final String json) {
        assertEquals(expected, serializer.fromJson(json, SessionScheme.class));
    }

    @Test
    void shouldExposeTheEightSchemesDefinedBySpec() {
        assertEquals(8, SessionScheme.values().length);
    }

    @Test
    void shouldRoundTripAllValues() {
        for (final SessionScheme value : SessionScheme.values()) {
            assertEquals(value, serializer.fromJson(serializer.toJson(value), SessionScheme.class));
        }
    }

    /**
     * Gson resolves an unrecognised enum value to null rather than throwing, so a missing scheme
     * would silently drop the required {@code scheme} field on a session response.
     */
    @ParameterizedTest
    @MethodSource("sessionSchemes")
    void shouldDeserializeEverySchemeOnGetSessionResponse(final SessionScheme expected, final String json) {
        final GetSessionResponse response =
                serializer.fromJson("{\"scheme\":" + json + "}", GetSessionResponse.class);

        assertNotNull(response);
        assertEquals(expected, response.getScheme());
    }

    @ParameterizedTest
    @MethodSource("sessionSchemes")
    void shouldDeserializeEverySchemeOnCreateSessionAcceptedResponse(final SessionScheme expected,
                                                                    final String json) {
        final CreateSessionAcceptedResponse response =
                serializer.fromJson("{\"scheme\":" + json + "}", CreateSessionAcceptedResponse.class);

        assertNotNull(response);
        assertEquals(expected, response.getScheme());
    }

}
