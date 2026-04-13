package com.checkout.payments.contexts;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression tests for date fields in payment-contexts classes.
 *
 * <p>These fields were previously typed as {@link java.time.Instant}, which caused
 * {@code sender_invalid} / validation errors because the API expects {@code yyyy-MM-dd}
 * (format: date) but {@code Instant} serializes as ISO-8601 datetime
 * (e.g. {@code 1990-05-26T00:00:00Z}).
 *
 * <p>Affected classes fixed: {@link PaymentContextsPassenger},
 * {@link PaymentContextsGuests}, {@link PaymentContextsTicket},
 * {@link PaymentContextsFlightLegDetails}, {@link PaymentContextsAccommodationData},
 * {@link PaymentContextsCustomerSummary}.
 */
class PaymentContextsDateSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ─── PaymentContextsPassenger ────────────────────────────────────────────

    @Test
    void passengerDateOfBirthShouldSerializeAsDateNotDatetime() {
        final PaymentContextsPassenger passenger = PaymentContextsPassenger.builder()
                .firstName("John")
                .lastName("White")
                .dateOfBirth(LocalDate.of(1990, 5, 26))
                .build();

        final String json = serializer.toJson(passenger);

        assertTrue(json.contains("\"date_of_birth\":\"1990-05-26\""),
                "Expected yyyy-MM-dd format but got: " + json);
        assertFalse(json.contains("T00:00:00Z"),
                "date_of_birth must NOT be an ISO-8601 datetime: " + json);
    }

    @Test
    void passengerDateOfBirthShouldRoundTrip() {
        final PaymentContextsPassenger original = PaymentContextsPassenger.builder()
                .firstName("John")
                .lastName("White")
                .dateOfBirth(LocalDate.of(1990, 5, 26))
                .build();

        final String json = serializer.toJson(original);
        final PaymentContextsPassenger deserialized = serializer.fromJson(json, PaymentContextsPassenger.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(1990, 5, 26), deserialized.getDateOfBirth());
        assertEquals("John", deserialized.getFirstName());
        assertEquals("White", deserialized.getLastName());
    }

    // ─── PaymentContextsGuests ───────────────────────────────────────────────

    @Test
    void guestDateOfBirthShouldSerializeAsDateNotDatetime() {
        final PaymentContextsGuests guest = PaymentContextsGuests.builder()
                .firstName("Jane")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1985, 7, 14))
                .build();

        final String json = serializer.toJson(guest);

        assertTrue(json.contains("\"date_of_birth\":\"1985-07-14\""),
                "Expected yyyy-MM-dd format but got: " + json);
        assertFalse(json.contains("T00:00:00Z"),
                "date_of_birth must NOT be an ISO-8601 datetime: " + json);
    }

    @Test
    void guestDateOfBirthShouldRoundTrip() {
        final PaymentContextsGuests original = PaymentContextsGuests.builder()
                .firstName("Jane")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1985, 7, 14))
                .build();

        final String json = serializer.toJson(original);
        final PaymentContextsGuests deserialized = serializer.fromJson(json, PaymentContextsGuests.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(1985, 7, 14), deserialized.getDateOfBirth());
    }

    // ─── PaymentContextsTicket ───────────────────────────────────────────────

    @Test
    void ticketIssueDateShouldSerializeAsDateNotDatetime() {
        final PaymentContextsTicket ticket = PaymentContextsTicket.builder()
                .number("045-21351455613")
                .issueDate(LocalDate.of(2023, 5, 20))
                .issuingCarrierCode("AI")
                .build();

        final String json = serializer.toJson(ticket);

        assertTrue(json.contains("\"issue_date\":\"2023-05-20\""),
                "Expected yyyy-MM-dd format but got: " + json);
        assertFalse(json.contains("T00:00:00Z"),
                "issue_date must NOT be an ISO-8601 datetime: " + json);
    }

    @Test
    void ticketIssueDateShouldRoundTrip() {
        final PaymentContextsTicket original = PaymentContextsTicket.builder()
                .number("045-21351455613")
                .issueDate(LocalDate.of(2023, 5, 20))
                .issuingCarrierCode("AI")
                .travelPackageIndicator("B")
                .travelAgencyName("World Tours")
                .travelAgencyCode("01")
                .build();

        final String json = serializer.toJson(original);
        final PaymentContextsTicket deserialized = serializer.fromJson(json, PaymentContextsTicket.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2023, 5, 20), deserialized.getIssueDate());
        assertEquals("045-21351455613", deserialized.getNumber());
    }

    // ─── PaymentContextsFlightLegDetails ─────────────────────────────────────

    @Test
    void flightLegDepartureDateShouldSerializeAsDateNotDatetime() {
        final PaymentContextsFlightLegDetails leg = PaymentContextsFlightLegDetails.builder()
                .flightNumber("AA100")
                .carrierCode("AA")
                .departureAirport("LHR")
                .departureDate(LocalDate.of(2023, 6, 19))
                .departureTime("09:00")
                .arrivalAirport("JFK")
                .build();

        final String json = serializer.toJson(leg);

        assertTrue(json.contains("\"departure_date\":\"2023-06-19\""),
                "Expected yyyy-MM-dd format but got: " + json);
        assertFalse(json.contains("T00:00:00Z"),
                "departure_date must NOT be an ISO-8601 datetime: " + json);
    }

    @Test
    void flightLegDepartureDateShouldRoundTrip() {
        final PaymentContextsFlightLegDetails original = PaymentContextsFlightLegDetails.builder()
                .flightNumber("AA100")
                .carrierCode("AA")
                .departureAirport("LHR")
                .departureDate(LocalDate.of(2023, 6, 19))
                .departureTime("09:00")
                .arrivalAirport("JFK")
                .stopOverCode("X")
                .fareBasisCode("SPRSVR")
                .build();

        final String json = serializer.toJson(original);
        final PaymentContextsFlightLegDetails deserialized =
                serializer.fromJson(json, PaymentContextsFlightLegDetails.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2023, 6, 19), deserialized.getDepartureDate());
        assertEquals("AA100", deserialized.getFlightNumber());
    }

    // ─── PaymentContextsAccommodationData ────────────────────────────────────

    @Test
    void accommodationCheckInOutDatesShouldSerializeAsDateNotDatetime() {
        final PaymentContextsAccommodationData accommodation = PaymentContextsAccommodationData.builder()
                .name("Grand Hotel")
                .checkInDate(LocalDate.of(2023, 6, 20))
                .checkOutDate(LocalDate.of(2023, 6, 23))
                .numberOfRooms(2)
                .guests(Arrays.asList(
                        PaymentContextsGuests.builder()
                                .firstName("Jane")
                                .lastName("Doe")
                                .dateOfBirth(LocalDate.of(1985, 7, 14))
                                .build()))
                .build();

        final String json = serializer.toJson(accommodation);

        assertTrue(json.contains("\"check_in_date\":\"2023-06-20\""),
                "check_in_date expected yyyy-MM-dd but got: " + json);
        assertTrue(json.contains("\"check_out_date\":\"2023-06-23\""),
                "check_out_date expected yyyy-MM-dd but got: " + json);
        assertTrue(json.contains("\"date_of_birth\":\"1985-07-14\""),
                "guest date_of_birth expected yyyy-MM-dd but got: " + json);
        assertFalse(json.contains("T00:00:00Z"),
                "No date field should serialize as ISO-8601 datetime: " + json);
    }

    @Test
    void accommodationDatesShouldRoundTrip() {
        final PaymentContextsAccommodationData original = PaymentContextsAccommodationData.builder()
                .name("Grand Hotel")
                .bookingReference("BK-12345")
                .checkInDate(LocalDate.of(2023, 6, 20))
                .checkOutDate(LocalDate.of(2023, 6, 23))
                .numberOfRooms(2)
                .build();

        final String json = serializer.toJson(original);
        final PaymentContextsAccommodationData deserialized =
                serializer.fromJson(json, PaymentContextsAccommodationData.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2023, 6, 20), deserialized.getCheckInDate());
        assertEquals(LocalDate.of(2023, 6, 23), deserialized.getCheckOutDate());
        assertEquals("Grand Hotel", deserialized.getName());
    }

    // ─── PaymentContextsCustomerSummary ──────────────────────────────────────

    @Test
    void customerSummaryDatesShouldSerializeAsDateNotDatetime() {
        final PaymentContextsCustomerSummary summary = PaymentContextsCustomerSummary.builder()
                .registrationDate(LocalDate.of(2023, 5, 1))
                .firstTransactionDate(LocalDate.of(2023, 7, 1))
                .lastPaymentDate(LocalDate.of(2023, 8, 1))
                .totalOrderCount(5)
                .lastPaymentAmount(99.99f)
                .build();

        final String json = serializer.toJson(summary);

        assertTrue(json.contains("\"registration_date\":\"2023-05-01\""),
                "registration_date expected yyyy-MM-dd but got: " + json);
        assertTrue(json.contains("\"first_transaction_date\":\"2023-07-01\""),
                "first_transaction_date expected yyyy-MM-dd but got: " + json);
        assertTrue(json.contains("\"last_payment_date\":\"2023-08-01\""),
                "last_payment_date expected yyyy-MM-dd but got: " + json);
        assertFalse(json.contains("T00:00:00Z"),
                "No date field should serialize as ISO-8601 datetime: " + json);
    }

    @Test
    void customerSummaryDatesShouldRoundTrip() {
        final PaymentContextsCustomerSummary original = PaymentContextsCustomerSummary.builder()
                .registrationDate(LocalDate.of(2023, 5, 1))
                .firstTransactionDate(LocalDate.of(2023, 7, 1))
                .lastPaymentDate(LocalDate.of(2023, 8, 1))
                .totalOrderCount(5)
                .lastPaymentAmount(99.99f)
                .build();

        final String json = serializer.toJson(original);
        final PaymentContextsCustomerSummary deserialized =
                serializer.fromJson(json, PaymentContextsCustomerSummary.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2023, 5, 1), deserialized.getRegistrationDate());
        assertEquals(LocalDate.of(2023, 7, 1), deserialized.getFirstTransactionDate());
        assertEquals(LocalDate.of(2023, 8, 1), deserialized.getLastPaymentDate());
        assertEquals(5, deserialized.getTotalOrderCount());
    }
}
