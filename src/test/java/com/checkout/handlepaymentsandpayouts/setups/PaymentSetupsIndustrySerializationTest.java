package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.AccommodationData;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.AccommodationHost;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.AirlineData;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.AirlineInsurance;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.AirlineInsurancePrice;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.Industry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentSetupsIndustrySerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeIndustryUnderCorrectJsonKeys() {
        final Industry industry = Industry.builder()
                .airlineData(AirlineData.builder().totalNumberOfPassengers(2L).build())
                .accommodationData(Collections.singletonList(
                        AccommodationData.builder().name("Grand Hotel").build()))
                .build();

        final String json = serializer.toJson(industry);

        // The API exposes these as "airline" / "accommodation", not "airline_data" / "accommodation_data".
        assertTrue(json.contains("\"airline\""));
        assertTrue(json.contains("\"accommodation\""));
        assertTrue(!json.contains("\"airline_data\""));
        assertTrue(!json.contains("\"accommodation_data\""));
    }

    @Test
    void shouldSerializeAndDeserializeNewAccommodationFields() {
        final AccommodationData accommodation = AccommodationData.builder()
                .name("Grand Hotel")
                .bookingReference("BOOK-123")
                .checkInDate(LocalDate.of(2026, 10, 1))
                .checkOutDate(LocalDate.of(2026, 10, 5))
                .numberOfRooms(2L)
                .totalNumberOfGuests(4L)
                .refundable(true)
                .deliveryRecipient("jane.smith@example.com")
                .host(AccommodationHost.builder()
                        .registrationDate(LocalDate.of(2020, 1, 1))
                        .totalReservationCount(150L)
                        .build())
                .build();

        final String json = serializer.toJson(accommodation);
        assertTrue(json.contains("\"total_number_of_guests\":4"));
        assertTrue(json.contains("\"refundable\":true"));
        assertTrue(json.contains("\"delivery_recipient\":\"jane.smith@example.com\""));
        assertTrue(json.contains("\"registration_date\":\"2020-01-01\""));
        assertTrue(json.contains("\"total_reservation_count\":150"));

        final AccommodationData deserialized = serializer.fromJson(json, AccommodationData.class);
        assertEquals(4L, deserialized.getTotalNumberOfGuests());
        assertEquals(true, deserialized.getRefundable());
        assertEquals("jane.smith@example.com", deserialized.getDeliveryRecipient());
        assertNotNull(deserialized.getHost());
        assertEquals(150L, deserialized.getHost().getTotalReservationCount());
    }

    @Test
    void shouldSerializeAndDeserializeNewAirlineFields() {
        final AirlineData airline = AirlineData.builder()
                .totalNumberOfPassengers(3L)
                .travelType("international")
                .tripType("round_trip")
                .refundable(false)
                .deliveryRecipient("jane.smith@example.com")
                .ancillaries("extra_baggage")
                .insurance(AirlineInsurance.builder()
                        .type("travel")
                        .company("Acme Insurance")
                        .price(AirlineInsurancePrice.builder().amount(25.5).currency(Currency.USD).build())
                        .build())
                .build();

        final String json = serializer.toJson(airline);
        assertTrue(json.contains("\"total_number_of_passengers\":3"));
        assertTrue(json.contains("\"travel_type\":\"international\""));
        assertTrue(json.contains("\"trip_type\":\"round_trip\""));
        assertTrue(json.contains("\"refundable\":false"));
        assertTrue(json.contains("\"delivery_recipient\":\"jane.smith@example.com\""));
        assertTrue(json.contains("\"ancillaries\":\"extra_baggage\""));
        assertTrue(json.contains("\"insurance\""));

        final AirlineData deserialized = serializer.fromJson(json, AirlineData.class);
        assertEquals(3L, deserialized.getTotalNumberOfPassengers());
        assertEquals("international", deserialized.getTravelType());
        assertEquals("round_trip", deserialized.getTripType());
        assertEquals(false, deserialized.getRefundable());
        assertEquals("extra_baggage", deserialized.getAncillaries());
        assertNotNull(deserialized.getInsurance());
        assertEquals("Acme Insurance", deserialized.getInsurance().getCompany());
        assertEquals(Currency.USD, deserialized.getInsurance().getPrice().getCurrency());
    }
}
