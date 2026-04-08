package com.checkout.handlepaymentsandpayouts.googlepay;

import com.checkout.GsonSerializer;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayEnrollmentRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayRegisterDomainRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayDomainListResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentState;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentStateResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema / serialization tests for Google Pay request and response DTOs.
 * Validates {@link com.google.gson.annotations.SerializedName} wiring and {@link java.time.Instant} handling.
 */
class GooglePaySerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // --- GooglePayEnrollmentRequest ---

    @Test
    void shouldSerializeGooglePayEnrollmentRequestWithSnakeCaseFieldNames() {
        GooglePayEnrollmentRequest request = GooglePayEnrollmentRequest.builder()
                .entityId("ent_abc123")
                .emailAddress("merchant@example.com")
                .acceptTermsOfService(true)
                .build();

        assertDoesNotThrow(() -> {
            String json = serializer.toJson(request);
            assertNotNull(json);
            assertTrue(json.contains("\"entity_id\""), "JSON should contain entity_id");
            assertTrue(json.contains("\"email_address\""), "JSON should contain email_address");
            assertTrue(json.contains("\"accept_terms_of_service\""), "JSON should contain accept_terms_of_service");
            assertTrue(json.contains("ent_abc123"), "JSON should contain entity id value");
            assertTrue(json.contains("merchant@example.com"), "JSON should contain email value");
            assertTrue(json.contains("true"), "JSON should contain accept_terms_of_service true");
        });
    }

    @Test
    void shouldRoundTripGooglePayEnrollmentRequest() {
        GooglePayEnrollmentRequest original = GooglePayEnrollmentRequest.builder()
                .entityId("ent_xyz")
                .emailAddress("user@shop.example.com")
                .acceptTermsOfService(Boolean.TRUE)
                .build();

        String json = serializer.toJson(original);
        GooglePayEnrollmentRequest deserialized = serializer.fromJson(json, GooglePayEnrollmentRequest.class);

        assertNotNull(deserialized);
        assertEquals(original.getEntityId(), deserialized.getEntityId());
        assertEquals(original.getEmailAddress(), deserialized.getEmailAddress());
        assertEquals(original.getAcceptTermsOfService(), deserialized.getAcceptTermsOfService());
    }

    @Test
    void shouldDeserializeSwaggerExampleGooglePayEnrollmentRequest() {
        String swaggerJson = "{"
                + "\"entity_id\":\"ent_swagger_1\","
                + "\"email_address\":\"owner@example.com\","
                + "\"accept_terms_of_service\":true"
                + "}";

        GooglePayEnrollmentRequest request = serializer.fromJson(swaggerJson, GooglePayEnrollmentRequest.class);

        assertNotNull(request);
        assertEquals("ent_swagger_1", request.getEntityId());
        assertEquals("owner@example.com", request.getEmailAddress());
        assertEquals(Boolean.TRUE, request.getAcceptTermsOfService());
    }

    // --- GooglePayRegisterDomainRequest ---

    @Test
    void shouldSerializeGooglePayRegisterDomainRequestWithWebDomainSnakeCase() {
        GooglePayRegisterDomainRequest request = GooglePayRegisterDomainRequest.builder()
                .webDomain("pay.example.com")
                .build();

        String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"web_domain\""), "JSON should contain web_domain");
        assertTrue(json.contains("pay.example.com"), "JSON should contain domain value");
    }

    @Test
    void shouldRoundTripGooglePayRegisterDomainRequest() {
        GooglePayRegisterDomainRequest original = GooglePayRegisterDomainRequest.builder()
                .webDomain("checkout.merchant.com")
                .build();

        String json = serializer.toJson(original);
        GooglePayRegisterDomainRequest deserialized = serializer.fromJson(json, GooglePayRegisterDomainRequest.class);

        assertNotNull(deserialized);
        assertEquals(original.getWebDomain(), deserialized.getWebDomain());
    }

    @Test
    void shouldDeserializeSwaggerExampleGooglePayRegisterDomainRequest() {
        String swaggerJson = "{\"web_domain\":\"registered.example.com\"}";

        GooglePayRegisterDomainRequest request = serializer.fromJson(swaggerJson, GooglePayRegisterDomainRequest.class);

        assertNotNull(request);
        assertEquals("registered.example.com", request.getWebDomain());
    }

    // --- GooglePayEnrollmentResponse ---

    @Test
    void shouldDeserializeGooglePayEnrollmentResponseWithInactiveState() {
        String json = "{"
                + "\"tos_accepted_time\":\"2023-01-01T00:00:00Z\","
                + "\"state\":\"INACTIVE\""
                + "}";

        GooglePayEnrollmentResponse response = serializer.fromJson(json, GooglePayEnrollmentResponse.class);

        assertNotNull(response);
        assertEquals(Instant.parse("2023-01-01T00:00:00Z"), response.getTosAcceptedTime());
        assertEquals(GooglePayEnrollmentState.INACTIVE, response.getState());
    }

    @Test
    void shouldRoundTripGooglePayEnrollmentResponse() {
        GooglePayEnrollmentResponse original = new GooglePayEnrollmentResponse();
        original.setTosAcceptedTime(Instant.parse("2024-10-02T15:01:23Z"));
        original.setState(GooglePayEnrollmentState.ACTIVE);

        String json = serializer.toJson(original);
        GooglePayEnrollmentResponse deserialized = serializer.fromJson(json, GooglePayEnrollmentResponse.class);

        assertNotNull(deserialized);
        assertEquals(original.getTosAcceptedTime(), deserialized.getTosAcceptedTime());
        assertEquals(original.getState(), deserialized.getState());
    }

    @Test
    void shouldDeserializeSwaggerExampleGooglePayEnrollmentResponse() {
        String swaggerJson = "{"
                + "\"tos_accepted_time\":\"2024-10-02T15:01:23Z\","
                + "\"state\":\"ACTIVE\""
                + "}";

        GooglePayEnrollmentResponse response = serializer.fromJson(swaggerJson, GooglePayEnrollmentResponse.class);

        assertNotNull(response);
        assertEquals(Instant.parse("2024-10-02T15:01:23Z"), response.getTosAcceptedTime());
        assertEquals(GooglePayEnrollmentState.ACTIVE, response.getState());
    }

    // --- GooglePayEnrollmentStateResponse ---

    @Test
    void shouldDeserializeGooglePayEnrollmentStateResponseActive() {
        String json = "{\"state\":\"ACTIVE\"}";

        GooglePayEnrollmentStateResponse response = serializer.fromJson(json, GooglePayEnrollmentStateResponse.class);

        assertNotNull(response);
        assertEquals(GooglePayEnrollmentState.ACTIVE, response.getState());
    }

    @Test
    void shouldDeserializeGooglePayEnrollmentStateResponseInactive() {
        String json = "{\"state\":\"INACTIVE\"}";

        GooglePayEnrollmentStateResponse response = serializer.fromJson(json, GooglePayEnrollmentStateResponse.class);

        assertNotNull(response);
        assertEquals(GooglePayEnrollmentState.INACTIVE, response.getState());
    }

    @Test
    void shouldRoundTripGooglePayEnrollmentStateResponse() {
        GooglePayEnrollmentStateResponse original = new GooglePayEnrollmentStateResponse();
        original.setState(GooglePayEnrollmentState.INACTIVE);

        String json = serializer.toJson(original);
        GooglePayEnrollmentStateResponse deserialized = serializer.fromJson(json, GooglePayEnrollmentStateResponse.class);

        assertNotNull(deserialized);
        assertEquals(original.getState(), deserialized.getState());
    }

    // --- GooglePayDomainListResponse ---

    @Test
    void shouldDeserializeGooglePayDomainListResponse() {
        String json = "{\"domains\":[\"example.com\",\"shop.example.com\"]}";

        GooglePayDomainListResponse response = serializer.fromJson(json, GooglePayDomainListResponse.class);

        assertNotNull(response);
        assertNotNull(response.getDomains());
        assertEquals(2, response.getDomains().size());
        assertEquals("example.com", response.getDomains().get(0));
        assertEquals("shop.example.com", response.getDomains().get(1));
    }

    @Test
    void shouldRoundTripGooglePayDomainListResponse() {
        GooglePayDomainListResponse original = new GooglePayDomainListResponse();
        original.setDomains(Arrays.asList("example.com", "shop.example.com"));

        String json = serializer.toJson(original);
        GooglePayDomainListResponse deserialized = serializer.fromJson(json, GooglePayDomainListResponse.class);

        assertNotNull(deserialized);
        assertNotNull(deserialized.getDomains());
        assertEquals(original.getDomains(), deserialized.getDomains());
    }

    @Test
    void shouldDeserializeSwaggerExampleGooglePayDomainListResponse() {
        String swaggerJson = "{\"domains\":[\"example.com\",\"shop.example.com\"]}";

        GooglePayDomainListResponse response = serializer.fromJson(swaggerJson, GooglePayDomainListResponse.class);

        assertNotNull(response);
        assertEquals(Arrays.asList("example.com", "shop.example.com"), response.getDomains());
    }

    @Test
    void shouldSerializeGooglePayEnrollmentRequestWithAcceptTermsFalse() {
        GooglePayEnrollmentRequest request = GooglePayEnrollmentRequest.builder()
                .entityId("ent_1")
                .emailAddress("a@b.co")
                .acceptTermsOfService(false)
                .build();

        String json = serializer.toJson(request);
        assertTrue(json.contains("false"));
        GooglePayEnrollmentRequest back = serializer.fromJson(json, GooglePayEnrollmentRequest.class);
        assertEquals(Boolean.FALSE, back.getAcceptTermsOfService());
    }

    @Test
    void shouldDeserializeGooglePayDomainListResponseEmptyList() {
        String json = "{\"domains\":[]}";

        GooglePayDomainListResponse response = serializer.fromJson(json, GooglePayDomainListResponse.class);

        assertNotNull(response);
        assertNotNull(response.getDomains());
        assertTrue(response.getDomains().isEmpty());
    }
}
