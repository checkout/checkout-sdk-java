package com.checkout.forward.requests;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for CreateSecretRequest and UpdateSecretRequest.
 * Verifies snake_case JSON mapping, required/optional fields and round-trip serialization.
 * Fields verified against com.checkout.forward.requests classes and shared/swagger.json.
 */
class SecretRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // CreateSecretRequest - swagger CreateSecretPlaintextRequest requires name + value.

    @Test
    void shouldSerializeCreateSecretWithRequiredFields() {
        final CreateSecretRequest request = CreateSecretRequest.builder()
                .name("my_secret")
                .value("s3cr3t-value")
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"my_secret\""));
        assertTrue(json.contains("\"value\":\"s3cr3t-value\""));
        // entity_id is optional and unset, so it must be absent
        assertFalse(json.contains("entity_id"));
    }

    @Test
    void shouldSerializeCreateSecretWithAllFields() {
        final CreateSecretRequest request = CreateSecretRequest.builder()
                .name("my_secret")
                .value("s3cr3t-value")
                .entityId("ent_1234567890")
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"my_secret\""));
        assertTrue(json.contains("\"value\":\"s3cr3t-value\""));
        assertTrue(json.contains("\"entity_id\":\"ent_1234567890\""));
    }

    @Test
    void shouldRoundTripCreateSecret() {
        final CreateSecretRequest original = CreateSecretRequest.builder()
                .name("my_secret")
                .value("s3cr3t-value")
                .entityId("ent_1234567890")
                .build();

        final String json = serializer.toJson(original);
        final CreateSecretRequest deserialized = serializer.fromJson(json, CreateSecretRequest.class);

        assertEquals(original, deserialized);
        assertEquals("my_secret", deserialized.getName());
        assertEquals("s3cr3t-value", deserialized.getValue());
        assertEquals("ent_1234567890", deserialized.getEntityId());
    }

    // UpdateSecretRequest - swagger requires at least one of value / entity_id.

    @Test
    void shouldSerializeUpdateSecretWithValueOnly() {
        final UpdateSecretRequest request = UpdateSecretRequest.builder()
                .value("new-value")
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"value\":\"new-value\""));
        assertFalse(json.contains("entity_id"));
    }

    @Test
    void shouldSerializeUpdateSecretWithEntityIdOnly() {
        final UpdateSecretRequest request = UpdateSecretRequest.builder()
                .entityId("ent_1234567890")
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"entity_id\":\"ent_1234567890\""));
        assertFalse(json.contains("\"value\""));
    }

    @Test
    void shouldSerializeUpdateSecretWithAllFields() {
        final UpdateSecretRequest request = UpdateSecretRequest.builder()
                .value("new-value")
                .entityId("ent_1234567890")
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"value\":\"new-value\""));
        assertTrue(json.contains("\"entity_id\":\"ent_1234567890\""));
    }

    @Test
    void shouldRoundTripUpdateSecret() {
        final UpdateSecretRequest original = UpdateSecretRequest.builder()
                .value("new-value")
                .entityId("ent_1234567890")
                .build();

        final String json = serializer.toJson(original);
        final UpdateSecretRequest deserialized = serializer.fromJson(json, UpdateSecretRequest.class);

        assertEquals(original, deserialized);
        assertEquals("new-value", deserialized.getValue());
        assertEquals("ent_1234567890", deserialized.getEntityId());
    }

}
