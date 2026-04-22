package com.checkout.payments;

import com.checkout.GsonSerializer;
import com.checkout.common.ThreeDSEnrollmentStatus;
import org.junit.jupiter.api.Test;
import static com.checkout.payments.TrustedListingStatus.ALLOWLISTED;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreeDSDataSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeMinimalResponse() {
        final String json = "{\"downgraded\":false}";

        final ThreeDSData data = serializer.fromJson(json, ThreeDSData.class);

        assertNotNull(data);
        assertEquals(false, data.getDowngraded());
    }

    @Test
    void shouldDeserializeEciField() {
        final String json = "{"
                + "\"eci\":\"05\","
                + "\"challenged\":true,"
                + "\"version\":\"2.1.0\""
                + "}";

        final ThreeDSData data = serializer.fromJson(json, ThreeDSData.class);

        assertNotNull(data);
        assertEquals("05", data.getEci());
        assertTrue(data.getChallenged());
        assertEquals("2.1.0", data.getVersion());
    }

    @Test
    void shouldDeserializeAuthenticationStatusReason() {
        final String json = "{"
                + "\"authentication_status_reason\":\"01\","
                + "\"eci\":\"06\""
                + "}";

        final ThreeDSData data = serializer.fromJson(json, ThreeDSData.class);

        assertNotNull(data);
        assertEquals("01", data.getAuthenticationStatusReason());
        assertEquals("06", data.getEci());
    }

    @Test
    void shouldDeserializeTrustedListing() {
        final String json = "{"
                + "\"trusted_listing\":{"
                + "  \"status\":\"Y\","
                + "  \"source\":\"01\""
                + "}"
                + "}";

        final ThreeDSData data = serializer.fromJson(json, ThreeDSData.class);

        assertNotNull(data);
        assertNotNull(data.getTrustedListing());
        assertEquals(ALLOWLISTED, data.getTrustedListing().getStatus());
        assertEquals("01", data.getTrustedListing().getSource());
    }

    @Test
    void shouldDeserializeFullResponse() {
        final String json = "{"
                + "\"downgraded\":false,"
                + "\"enrolled\":\"Y\","
                + "\"upgrade_reason\":\"02\","
                + "\"signature_valid\":\"Y\","
                + "\"authentication_response\":\"Y\","
                + "\"cryptogram\":\"AgAAAAAAAIR8CQrXcIhbQAAAAAA\","
                + "\"xid\":\"MDAwMDAwMDAwMDAwMDAwMzIyNzY\","
                + "\"version\":\"2.1.0\","
                + "\"exemption_applied\":\"low_value\","
                + "\"challenged\":true,"
                + "\"eci\":\"05\","
                + "\"authentication_status_reason\":\"01\","
                + "\"trusted_listing\":{"
                + "  \"status\":\"Y\","
                + "  \"source\":\"01\""
                + "}"
                + "}";

        final ThreeDSData data = serializer.fromJson(json, ThreeDSData.class);

        assertNotNull(data);
        assertEquals(false, data.getDowngraded());
        assertEquals(ThreeDSEnrollmentStatus.ISSUER_ENROLLED, data.getEnrolled());
        assertEquals("02", data.getUpgradeReason());
        assertEquals("Y", data.getSignatureValid());
        assertEquals("Y", data.getAuthenticationResponse());
        assertEquals("AgAAAAAAAIR8CQrXcIhbQAAAAAA", data.getCryptogram());
        assertEquals("MDAwMDAwMDAwMDAwMDAwMzIyNzY", data.getXid());
        assertEquals("2.1.0", data.getVersion());
        assertEquals("low_value", data.getExemptionApplied());
        assertTrue(data.getChallenged());
        assertEquals("05", data.getEci());
        assertEquals("01", data.getAuthenticationStatusReason());
        assertNotNull(data.getTrustedListing());
        assertEquals(ALLOWLISTED, data.getTrustedListing().getStatus());
    }

    @Test
    void shouldRoundTripSerialize() {
        final String original = "{"
                + "\"eci\":\"05\","
                + "\"authentication_status_reason\":\"01\","
                + "\"trusted_listing\":{\"status\":\"Y\",\"source\":\"01\"},"
                + "\"challenged\":true,"
                + "\"version\":\"2.2.0\""
                + "}";

        final ThreeDSData data = serializer.fromJson(original, ThreeDSData.class);
        final String reserialised = serializer.toJson(data);
        final ThreeDSData roundTripped = serializer.fromJson(reserialised, ThreeDSData.class);

        assertNotNull(roundTripped);
        assertEquals(data.getEci(), roundTripped.getEci());
        assertEquals(data.getAuthenticationStatusReason(), roundTripped.getAuthenticationStatusReason());
        assertEquals(data.getTrustedListing().getStatus(), roundTripped.getTrustedListing().getStatus());
        assertEquals(data.getChallenged(), roundTripped.getChallenged());
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{}";

        final ThreeDSData data = serializer.fromJson(json, ThreeDSData.class);

        assertNotNull(data);
        assertDoesNotThrow(() -> serializer.toJson(data));
        assertNull(data.getEci());
        assertNull(data.getAuthenticationStatusReason());
        assertNull(data.getTrustedListing());
        assertNull(data.getChallenged());
    }
}
