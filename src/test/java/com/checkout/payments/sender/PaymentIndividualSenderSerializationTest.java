package com.checkout.payments.sender;

import com.checkout.GsonSerializer;
import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderIdentificationType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema serialization tests for PaymentIndividualSender.
 *
 * <p>Verifies that {@code dateOfBirth} serializes as {@code yyyy-MM-dd} (not an ISO 8601
 * timestamp), matching the Checkout.com API expectation for the {@code date_of_birth} field
 * (swagger format: date). This was the root cause of issue #522.</p>
 */
class PaymentIndividualSenderSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithRequiredFields() {
        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("123 Main St")
                        .city("London")
                        .country(CountryCode.GB)
                        .build())
                .build();

        assertDoesNotThrow(() -> serializer.toJson(sender));
    }

    @Test
    void shouldSerializeWithAllOptionalFields() {
        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .middleName("James")
                .lastName("Doe")
                .dob("1990-05-15")
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .address(Address.builder()
                        .addressLine1("123 Main St")
                        .addressLine2("Apt 4B")
                        .city("London")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .identification(AccountHolderIdentification.builder()
                        .type(AccountHolderIdentificationType.DRIVING_LICENSE)
                        .number("DL1234567")
                        .issuingCountry(CountryCode.GB)
                        .build())
                .referenceType("pep")
                .sourceOfFunds(SourceOfFunds.CREDIT)
                .countryOfBirth(CountryCode.GB)
                .nationality(CountryCode.GB)
                .reference("ref-001")
                .build();

        assertDoesNotThrow(() -> serializer.toJson(sender));
    }

    /**
     * This is the core regression test for issue #522.
     *
     * <p>When {@code dateOfBirth} was {@code Instant}, it serialized as
     * {@code "1990-05-15T00:00:00Z"} which the API rejected with a 422 sender_invalid error.
     * Now that it is {@code LocalDate}, the GsonSerializer formats it as {@code "1990-05-15"},
     * which is the format the API expects.</p>
     */
    @Test
    void shouldSerializeDateOfBirthAsYyyyMmDdNotAsTimestamp() {
        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .address(Address.builder()
                        .addressLine1("123 Main St")
                        .city("London")
                        .country(CountryCode.GB)
                        .build())
                .build();

        final String json = serializer.toJson(sender);

        assertTrue(json.contains("\"1990-05-15\""),
                "dateOfBirth must serialize as yyyy-MM-dd, not as ISO timestamp. Got: " + json);
        assertTrue(json.contains("date_of_birth"),
                "Should use snake_case field name 'date_of_birth'. Got: " + json);
    }

    @Test
    void shouldRoundTripSerialize() {
        final PaymentIndividualSender original = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .address(Address.builder()
                        .addressLine1("123 Main St")
                        .city("London")
                        .country(CountryCode.GB)
                        .build())
                .build();

        final String json = serializer.toJson(original);
        final PaymentIndividualSender deserialized = serializer.fromJson(json, PaymentIndividualSender.class);

        assertNotNull(deserialized);
        assertEquals(original.getFirstName(), deserialized.getFirstName());
        assertEquals(original.getLastName(), deserialized.getLastName());
        assertEquals(original.getDateOfBirth(), deserialized.getDateOfBirth());
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final String swaggerJson = "{"
                + "\"type\":\"individual\","
                + "\"first_name\":\"John\","
                + "\"last_name\":\"Jones\","
                + "\"date_of_birth\":\"1985-05-15\","
                + "\"address\":{"
                + "  \"address_line1\":\"123 Main St\","
                + "  \"city\":\"London\","
                + "  \"country\":\"GB\""
                + "},"
                + "\"reference\":\"ref-001\""
                + "}";

        final PaymentIndividualSender sender = serializer.fromJson(swaggerJson, PaymentIndividualSender.class);

        assertNotNull(sender);
        assertEquals("John", sender.getFirstName());
        assertEquals("Jones", sender.getLastName());
        assertEquals(LocalDate.of(1985, 5, 15), sender.getDateOfBirth());
    }

    @Test
    void shouldHandleNullDateOfBirth() {
        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(null)
                .address(Address.builder()
                        .addressLine1("123 Main St")
                        .city("London")
                        .country(CountryCode.GB)
                        .build())
                .build();

        final String json = serializer.toJson(sender);

        assertDoesNotThrow(() -> serializer.toJson(sender));
        // null dateOfBirth should be omitted from the serialized JSON
        assertTrue(!json.contains("date_of_birth") || json.contains("\"date_of_birth\":null"),
                "null dateOfBirth should be omitted or null in JSON. Got: " + json);
    }
}
