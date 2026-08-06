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
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionChallengeIndicatorTest {

    private final Serializer serializer = new GsonSerializer();

    private static Stream<Arguments> sessionChallengeIndicators() {
        return Stream.of(
                Arguments.of(SessionChallengeIndicator.NO_PREFERENCE, "\"no_preference\""),
                Arguments.of(SessionChallengeIndicator.NO_CHALLENGE_REQUESTED, "\"no_challenge_requested\""),
                Arguments.of(SessionChallengeIndicator.CHALLENGE_REQUESTED, "\"challenge_requested\""),
                Arguments.of(SessionChallengeIndicator.CHALLENGE_REQUESTED_MANDATE, "\"challenge_requested_mandate\""),
                Arguments.of(SessionChallengeIndicator.LOW_VALUE, "\"low_value\""),
                Arguments.of(SessionChallengeIndicator.TRUSTED_LISTING, "\"trusted_listing\""),
                Arguments.of(SessionChallengeIndicator.TRUSTED_LISTING_PROMPT, "\"trusted_listing_prompt\""),
                Arguments.of(SessionChallengeIndicator.TRANSACTION_RISK_ASSESSMENT, "\"transaction_risk_assessment\""),
                Arguments.of(SessionChallengeIndicator.DATA_SHARE, "\"data_share\"")
        );
    }

    @ParameterizedTest
    @MethodSource("sessionChallengeIndicators")
    void shouldSerializeSessionChallengeIndicatorToSnakeCase(final SessionChallengeIndicator value,
                                                             final String expectedJson) {
        assertEquals(expectedJson, serializer.toJson(value));
    }

    @ParameterizedTest
    @MethodSource("sessionChallengeIndicators")
    void shouldDeserializeSessionChallengeIndicatorFromSnakeCase(final SessionChallengeIndicator expected,
                                                                 final String json) {
        assertEquals(expected, serializer.fromJson(json, SessionChallengeIndicator.class));
    }

    @Test
    void shouldRoundTripAllValues() {
        for (final SessionChallengeIndicator value : SessionChallengeIndicator.values()) {
            final String json = serializer.toJson(value);
            assertEquals(value, serializer.fromJson(json, SessionChallengeIndicator.class));
        }
    }

    @Test
    void shouldExposeTheNineValuesDefinedBySessionRequest() {
        assertEquals(9, SessionChallengeIndicator.values().length);
    }

    /**
     * {@link SessionRequest} is serialize-only: its {@code source} is an abstract
     * {@link com.checkout.sessions.source.SessionSource} with no registered Gson adapter, so the
     * request cannot be deserialized. Assert on the emitted JSON instead.
     */
    @ParameterizedTest
    @MethodSource("sessionChallengeIndicators")
    void shouldSerializeEveryValueOnSessionRequest(final SessionChallengeIndicator value,
                                                   final String expectedJson) {
        final SessionRequest request = SessionRequest.builder()
                .challengeIndicator(value)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"challenge_indicator\":" + expectedJson),
                "expected challenge_indicator " + expectedJson + " in " + json);
    }

    @Test
    void shouldDefaultSessionRequestChallengeIndicatorToNoPreference() {
        final SessionRequest request = SessionRequest.builder().build();

        assertEquals(SessionChallengeIndicator.NO_PREFERENCE, request.getChallengeIndicator());
        assertTrue(serializer.toJson(request).contains("\"challenge_indicator\":\"no_preference\""));
    }

    /**
     * The API Reference specifies only the four base values on the session responses, but the
     * request accepts all nine. This guards against a deserialization failure if the API echoes an
     * exemption value back.
     */
    @ParameterizedTest
    @MethodSource("sessionChallengeIndicators")
    void shouldDeserializeEveryValueOnGetSessionResponse(final SessionChallengeIndicator expected,
                                                         final String json) {
        final String responseJson = "{\"challenge_indicator\":" + json + "}";

        final GetSessionResponse response = serializer.fromJson(responseJson, GetSessionResponse.class);

        assertNotNull(response);
        assertEquals(expected, response.getChallengeIndicator());
    }

    @ParameterizedTest
    @MethodSource("sessionChallengeIndicators")
    void shouldDeserializeEveryValueOnCreateSessionAcceptedResponse(final SessionChallengeIndicator expected,
                                                                    final String json) {
        final String responseJson = "{\"challenge_indicator\":" + json + "}";

        final CreateSessionAcceptedResponse response =
                serializer.fromJson(responseJson, CreateSessionAcceptedResponse.class);

        assertNotNull(response);
        assertEquals(expected, response.getChallengeIndicator());
    }

}
