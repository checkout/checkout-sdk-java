package com.checkout.forward.responses;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Schema validation tests for SecretResponse and SecretsListResponse.
 * Verifies snake_case JSON mapping, Instant deserialization, and the list wrapper
 * under "data" returned by GET /secrets.
 * Fields verified against com.checkout.forward.responses classes and shared/swagger.json.
 */
class SecretResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeSecretResponse() {
        final String json = "{"
                + "\"name\":\"my_secret\","
                + "\"created_at\":\"2026-07-09T10:15:30Z\","
                + "\"updated_at\":\"2026-07-09T11:20:45Z\","
                + "\"version\":3,"
                + "\"entity_id\":\"ent_1234567890\""
                + "}";

        final SecretResponse response = serializer.fromJson(json, SecretResponse.class);

        assertNotNull(response);
        assertEquals("my_secret", response.getName());
        assertEquals(Instant.parse("2026-07-09T10:15:30Z"), response.getCreatedAt());
        assertEquals(Instant.parse("2026-07-09T11:20:45Z"), response.getUpdatedAt());
        assertEquals(Integer.valueOf(3), response.getVersion());
        assertEquals("ent_1234567890", response.getEntityId());
    }

    @Test
    void shouldRoundTripSecretResponse() {
        final SecretResponse original = new SecretResponse();
        original.setName("my_secret");
        original.setCreatedAt(Instant.parse("2026-07-09T10:15:30Z"));
        original.setUpdatedAt(Instant.parse("2026-07-09T11:20:45Z"));
        original.setVersion(3);
        original.setEntityId("ent_1234567890");

        final String json = serializer.toJson(original);
        final SecretResponse deserialized = serializer.fromJson(json, SecretResponse.class);

        assertEquals("my_secret", deserialized.getName());
        assertEquals(original.getCreatedAt(), deserialized.getCreatedAt());
        assertEquals(original.getUpdatedAt(), deserialized.getUpdatedAt());
        assertEquals(original.getVersion(), deserialized.getVersion());
        assertEquals(original.getEntityId(), deserialized.getEntityId());
    }

    @Test
    void shouldDeserializeSecretsListWrapper() {
        final String json = "{"
                + "\"data\":["
                + "{\"name\":\"secret_one\",\"version\":1,\"entity_id\":\"ent_one\"},"
                + "{\"name\":\"secret_two\",\"version\":2,\"entity_id\":\"ent_two\"}"
                + "]}";

        final SecretsListResponse response = serializer.fromJson(json, SecretsListResponse.class);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals("secret_one", response.getData().get(0).getName());
        assertEquals(Integer.valueOf(1), response.getData().get(0).getVersion());
        assertEquals("ent_one", response.getData().get(0).getEntityId());
        assertEquals("secret_two", response.getData().get(1).getName());
        assertEquals(Integer.valueOf(2), response.getData().get(1).getVersion());
    }

    @Test
    void shouldDeserializeEmptySecretsList() {
        final String json = "{\"data\":[]}";

        final SecretsListResponse response = serializer.fromJson(json, SecretsListResponse.class);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(0, response.getData().size());
    }

}
