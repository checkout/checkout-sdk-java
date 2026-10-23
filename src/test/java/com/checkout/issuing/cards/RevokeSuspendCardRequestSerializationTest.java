package com.checkout.issuing.cards;

import com.checkout.GsonSerializer;
import com.checkout.issuing.cards.requests.revoke.RevokeCardRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeReason;
import com.checkout.issuing.cards.requests.suspend.SuspendCardRequest;
import com.checkout.issuing.cards.requests.suspend.SuspendReason;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Serialization tests for the revoke-card-request and suspend-card-request schemas.
 *
 * Swagger reference: POST /issuing/cards/{cardId}/revoke and POST /issuing/cards/{cardId}/suspend.
 * Both bodies are optional and their only property, reason, is optional.
 */
class RevokeSuspendCardRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @ParameterizedTest
    @CsvSource({
            "EXPIRED,expired",
            "REPORTED_LOST,reported_lost",
            "REPORTED_STOLEN,reported_stolen"
    })
    void shouldRoundtripRevokeCardRequestForEveryReason(final RevokeReason reason, final String wire) {
        final RevokeCardRequest request = RevokeCardRequest.builder().reason(reason).build();

        final String json = serializer.toJson(request);
        assertEquals("{\"reason\":\"" + wire + "\"}", json);

        final RevokeCardRequest roundtrip = serializer.fromJson(json, RevokeCardRequest.class);
        assertEquals(request, roundtrip);
        assertEquals(reason, roundtrip.getReason());
    }

    @ParameterizedTest
    @CsvSource({
            "SUSPECTED_LOST,suspected_lost",
            "SUSPECTED_STOLEN,suspected_stolen"
    })
    void shouldRoundtripSuspendCardRequestForEveryReason(final SuspendReason reason, final String wire) {
        final SuspendCardRequest request = SuspendCardRequest.builder().reason(reason).build();

        final String json = serializer.toJson(request);
        assertEquals("{\"reason\":\"" + wire + "\"}", json);

        final SuspendCardRequest roundtrip = serializer.fromJson(json, SuspendCardRequest.class);
        assertEquals(request, roundtrip);
        assertEquals(reason, roundtrip.getReason());
    }

    @Test
    void shouldDeserializeTheSwaggerExamples() {
        assertEquals(RevokeReason.REPORTED_LOST,
                serializer.fromJson("{\"reason\":\"reported_lost\"}", RevokeCardRequest.class).getReason());
        assertEquals(SuspendReason.SUSPECTED_LOST,
                serializer.fromJson("{\"reason\":\"suspected_lost\"}", SuspendCardRequest.class).getReason());
    }

    @Test
    void shouldSerializeEmptyRequestsAsEmptyObjects() {
        assertEquals("{}", serializer.toJson(RevokeCardRequest.builder().build()));
        assertEquals("{}", serializer.toJson(SuspendCardRequest.builder().build()));
        assertNull(serializer.fromJson("{}", RevokeCardRequest.class).getReason());
        assertNull(serializer.fromJson("{}", SuspendCardRequest.class).getReason());
    }
}
