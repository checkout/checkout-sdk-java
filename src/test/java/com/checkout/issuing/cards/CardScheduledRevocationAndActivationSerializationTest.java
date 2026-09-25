package com.checkout.issuing.cards;

import com.checkout.GsonSerializer;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.update.UpdateCardRequest;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.cards.responses.VirtualCardDetailsResponse;
import com.checkout.issuing.cards.responses.activate.ActivateCardResponse;
import com.checkout.issuing.cards.responses.update.UpdateCardResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GSON serialization tests for the 2026-09-17 issuing card changes (INT-1700):
 * {@code scheduled_revocation_date}, {@code last_activated_on}, and the {@code status}
 * field on update-card-request.
 */
class CardScheduledRevocationAndActivationSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeScheduledRevocationDateOnAddCardRequest() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId("crh_test")
                .scheduledRevocationDate(LocalDate.of(2027, 3, 12))
                .revocationDate(LocalDate.of(2027, 1, 1))
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"scheduled_revocation_date\":\"2027-03-12\""));
        assertTrue(json.contains("\"revocation_date\":\"2027-01-01\""));
    }

    @Test
    void shouldSerializeStatusAndScheduledRevocationDateOnUpdateCardRequest() {
        final UpdateCardRequest request = UpdateCardRequest.builder()
                .status(CardStatus.ACTIVE)
                .scheduledRevocationDate(LocalDate.of(2027, 3, 12))
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"status\":\"active\""));
        assertTrue(json.contains("\"scheduled_revocation_date\":\"2027-03-12\""));
    }

    @Test
    void shouldRoundTripUpdateCardRequestWithStatusAndScheduledRevocationDate() {
        final UpdateCardRequest original = UpdateCardRequest.builder()
                .status(CardStatus.ACTIVE)
                .scheduledRevocationDate(LocalDate.of(2027, 3, 12))
                .build();

        final UpdateCardRequest deserialized =
                serializer.fromJson(serializer.toJson(original), UpdateCardRequest.class);

        assertEquals(original.getStatus(), deserialized.getStatus());
        assertEquals(original.getScheduledRevocationDate(), deserialized.getScheduledRevocationDate());
    }

    @Test
    void shouldDeserializeScheduledRevocationDateAndLastActivatedOnOnGetCardResponse() {
        final String json = "{\"type\":\"virtual\",\"id\":\"crd_test\",\"entity_id\":\"ent_test\","
                + "\"scheduled_revocation_date\":\"2027-03-12\","
                + "\"last_activated_on\":\"2019-09-10T10:11:12Z\"}";

        final VirtualCardDetailsResponse response =
                serializer.fromJson(json, VirtualCardDetailsResponse.class);

        assertNotNull(response);
        assertEquals(LocalDate.of(2027, 3, 12), response.getScheduledRevocationDate());
        assertEquals(Instant.parse("2019-09-10T10:11:12Z"), response.getLastActivatedOn());
    }

    @Test
    void shouldDeserializeNullLastActivatedOnOnGetCardResponseWhenCardNeverActivated() {
        final String json = "{\"type\":\"virtual\",\"id\":\"crd_test\",\"last_activated_on\":null}";

        final VirtualCardDetailsResponse response =
                serializer.fromJson(json, VirtualCardDetailsResponse.class);

        assertNotNull(response);
        assertNull(response.getLastActivatedOn());
    }

    @Test
    void shouldDeserializeLastActivatedOnOnAddCardResponse() {
        final String json = "{\"id\":\"crd_test\",\"last_activated_on\":\"2019-09-10T10:11:12Z\"}";

        final CardResponse response = serializer.fromJson(json, CardResponse.class);

        assertNotNull(response);
        assertEquals(Instant.parse("2019-09-10T10:11:12Z"), response.getLastActivatedOn());
    }

    @Test
    void shouldDeserializeActivateCardResponseSwaggerExample() {
        final String json = "{\"last_activated_on\":\"2019-09-10T10:11:12Z\"}";

        final ActivateCardResponse response = serializer.fromJson(json, ActivateCardResponse.class);

        assertNotNull(response);
        assertEquals(Instant.parse("2019-09-10T10:11:12Z"), response.getLastActivatedOn());
    }

    @Test
    void shouldDeserializeUpdateCardResponseWithFullGetCardResponseFieldsAndNoEncryptedCvv() {
        final String json = "{\"id\":\"crd_test\",\"cardholder_id\":\"crh_test\",\"client_id\":\"cli_test\","
                + "\"entity_id\":\"ent_test\",\"last_four\":\"4321\",\"expiry_month\":12,\"expiry_year\":2030,"
                + "\"status\":\"active\",\"type\":\"virtual\",\"billing_currency\":\"USD\","
                + "\"issuing_country\":\"US\",\"scheme\":\"visa\",\"scheduled_revocation_date\":\"2027-03-12\","
                + "\"last_activated_on\":\"2019-09-10T10:11:12Z\","
                + "\"last_modified_date\":\"2026-09-17T00:00:00Z\",\"encrypted_cvv\":\"should-be-ignored\"}";

        final UpdateCardResponse response = serializer.fromJson(json, UpdateCardResponse.class);

        assertNotNull(response);
        assertEquals("crd_test", response.getId());
        assertEquals(CardStatus.ACTIVE, response.getStatus());
        assertEquals(LocalDate.of(2027, 3, 12), response.getScheduledRevocationDate());
        assertEquals(Instant.parse("2019-09-10T10:11:12Z"), response.getLastActivatedOn());
        assertNotNull(response.getLastModifiedDate());
        assertFalse(serializer.toJson(response).contains("encrypted_cvv"));
    }

    // Verifies the 2026-09-23 spec update: update-card-response gained a virtual/physical
    // discriminator, and the virtual variant adds is_single_use.
    @Test
    void shouldDeserializeIsSingleUseOnVirtualCardUpdateResponse() {
        final String json = "{\"type\":\"virtual\",\"last_modified_date\":\"2019-09-10T10:11:12Z\","
                + "\"is_single_use\":true}";

        final UpdateCardResponse response = serializer.fromJson(json, UpdateCardResponse.class);

        assertNotNull(response);
        assertEquals(Boolean.TRUE, response.getIsSingleUse());
    }
}
