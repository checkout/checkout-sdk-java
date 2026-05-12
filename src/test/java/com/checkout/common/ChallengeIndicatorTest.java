package com.checkout.common;

import com.checkout.GsonSerializer;
import com.checkout.Serializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChallengeIndicatorTest {

    private final Serializer serializer = new GsonSerializer();

    private static Stream<Arguments> challengeIndicators() {
        return Stream.of(
                Arguments.of(ChallengeIndicator.NO_PREFERENCE, "\"no_preference\""),
                Arguments.of(ChallengeIndicator.NO_CHALLENGE_REQUESTED, "\"no_challenge_requested\""),
                Arguments.of(ChallengeIndicator.CHALLENGE_REQUESTED, "\"challenge_requested\""),
                Arguments.of(ChallengeIndicator.CHALLENGE_REQUESTED_MANDATE, "\"challenge_requested_mandate\""),
                Arguments.of(ChallengeIndicator.LOW_VALUE, "\"low_value\""),
                Arguments.of(ChallengeIndicator.TRUSTED_LISTING, "\"trusted_listing\""),
                Arguments.of(ChallengeIndicator.TRUSTED_LISTING_PROMPT, "\"trusted_listing_prompt\""),
                Arguments.of(ChallengeIndicator.TRANSACTION_RISK_ASSESSMENT, "\"transaction_risk_assessment\""),
                Arguments.of(ChallengeIndicator.DATA_SHARE, "\"data_share\"")
        );
    }

    @ParameterizedTest
    @MethodSource("challengeIndicators")
    void shouldSerializeChallengeIndicatorToSnakeCase(final ChallengeIndicator value, final String expectedJson) {
        assertEquals(expectedJson, serializer.toJson(value));
    }

    @ParameterizedTest
    @MethodSource("challengeIndicators")
    void shouldDeserializeChallengeIndicatorFromSnakeCase(final ChallengeIndicator expected, final String json) {
        assertEquals(expected, serializer.fromJson(json, ChallengeIndicator.class));
    }

    @Test
    void shouldRoundTripAllValues() {
        for (final ChallengeIndicator value : ChallengeIndicator.values()) {
            final String json = serializer.toJson(value);
            assertEquals(value, serializer.fromJson(json, ChallengeIndicator.class));
        }
    }

}
