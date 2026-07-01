package com.checkout.issuing.cards;

import com.checkout.GsonSerializer;
import com.checkout.issuing.cards.requests.create.ReturnCredentialsType;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.update.UpdateCardRequest;
import com.checkout.issuing.cards.responses.VirtualCardDetailsResponse;
import com.checkout.issuing.controls.requests.create.MccCardControlRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GSON serialization tests for the 2026-06-29 card activation/revocation date changes.
 * activation_date supports a round-hour datetime; revocation_date is date-only (yyyy-MM-dd).
 */
class CardActivationDateSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeActivationAndRevocationDateOnCreateRequest() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId("crh_test")
                .activationDate("2026-06-01T10:00Z")
                .revocationDate(LocalDate.of(2026, 7, 1))
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"activation_date\""));
        assertTrue(json.contains("2026-06-01T10:00Z"));
        assertTrue(json.contains("\"revocation_date\""));
        assertTrue(json.contains("2026-07-01"));
    }

    @Test
    void shouldSerializeActivationAndRevocationDateOnUpdateRequest() {
        final UpdateCardRequest request = UpdateCardRequest.builder()
                .reference("X-123")
                .activationDate("2026-06-01T10:00Z")
                .revocationDate(LocalDate.of(2026, 7, 1))
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"activation_date\""));
        assertTrue(json.contains("\"revocation_date\""));
        assertTrue(json.contains("2026-07-01"));
    }

    @Test
    void shouldRoundTripUpdateCardRequest() {
        final UpdateCardRequest original = UpdateCardRequest.builder()
                .activationDate("2026-06-01T10:00Z")
                .revocationDate(LocalDate.of(2026, 7, 1))
                .build();

        final UpdateCardRequest deserialized =
                serializer.fromJson(serializer.toJson(original), UpdateCardRequest.class);

        assertEquals(original.getActivationDate(), deserialized.getActivationDate());
        assertEquals(original.getRevocationDate(), deserialized.getRevocationDate());
    }

    @Test
    void shouldDeserializeActivationDateOnCardResponse() {
        final String json = "{\"type\":\"virtual\",\"id\":\"crd_test\",\"entity_id\":\"ent_test\","
                + "\"user_id\":\"usr_test\",\"scheme\":\"mastercard\",\"root_card_id\":\"crd_root\","
                + "\"parent_card_id\":\"crd_parent\",\"revocation_date\":\"2026-07-01\","
                + "\"activation_date\":\"2026-06-01T10:00Z\"}";

        final VirtualCardDetailsResponse response =
                serializer.fromJson(json, VirtualCardDetailsResponse.class);

        assertNotNull(response);
        assertEquals("2026-06-01T10:00Z", response.getActivationDate());
        assertEquals("ent_test", response.getEntityId());
        assertEquals("usr_test", response.getUserId());
        assertEquals(com.checkout.issuing.cards.IssuingScheme.MASTERCARD, response.getScheme());
        assertEquals("crd_root", response.getRootCardId());
        assertEquals("crd_parent", response.getParentCardId());
        assertEquals(java.time.LocalDate.of(2026, 7, 1), response.getRevocationDate());
    }

    @Test
    void shouldSerializeVirtualCardControlFieldsOnCreateRequest() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId("crh_test")
                .returnCredentials(Arrays.asList(ReturnCredentialsType.NUMBER, ReturnCredentialsType.CVC2))
                .controlProfiles(Collections.singletonList("ctrprf_test"))
                .controls(Collections.singletonList(
                        MccCardControlRequest.builder().description("MCC control").build()))
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"return_credentials\""));
        assertTrue(json.contains("\"number\""));
        assertTrue(json.contains("\"cvc2\""));
        assertTrue(json.contains("\"control_profiles\""));
        assertTrue(json.contains("ctrprf_test"));
        assertTrue(json.contains("\"controls\""));
        assertTrue(json.contains("\"control_type\":\"mcc_limit\""));
    }
}
