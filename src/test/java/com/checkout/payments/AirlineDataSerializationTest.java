package com.checkout.payments;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Serialization tests for {@link AirlineData} and its nested types.
 *
 * <p>processing.airline_data[].passenger is an array. Some SDKs typed it as a single object, so
 * every GET /payments/{id} response carrying passenger data failed to deserialize. Reported
 * internally. Java already had the list, but nothing exercised the field, and the surrounding
 * flight-leg shape was still the pre-NAS one: it shipped service_class instead of
 * class_of_travelling and stopover_code instead of stop_over_code, both keys the API does not
 * define, and flight_number as an integer where the spec declares a string.
 *
 * <p>The JSON fixture below is the swagger AirlineData example values, shared byte for byte with
 * the equivalent test in the other SDKs so that seven languages assert against one wire shape.
 */
class AirlineDataSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    private static final String AIRLINE_JSON = "{"
            + "\"ticket\":{"
            + "\"number\":\"045-21351455613\","
            + "\"issue_date\":\"2023-05-20\","
            + "\"issuing_carrier_code\":\"AI\","
            + "\"travel_package_indicator\":\"B\","
            + "\"travel_agency_name\":\"World Tours\","
            + "\"travel_agency_code\":\"01\""
            + "},"
            + "\"passenger\":[{"
            + "\"first_name\":\"John\","
            + "\"last_name\":\"White\","
            + "\"date_of_birth\":\"1990-05-26\","
            + "\"address\":{\"country\":\"US\"}"
            + "}],"
            + "\"flight_leg_details\":[{"
            + "\"flight_number\":\"101\","
            + "\"carrier_code\":\"BA\","
            + "\"class_of_travelling\":\"J\","
            + "\"departure_airport\":\"LHR\","
            + "\"departure_date\":\"2023-06-19\","
            + "\"departure_time\":\"15:30\","
            + "\"arrival_airport\":\"LAX\","
            + "\"stop_over_code\":\"x\","
            + "\"fare_basis_code\":\"SPRSVR\""
            + "}]"
            + "}";

    @Test
    void shouldDeserializeAirlineDataWithPassengerAsAnArray() {
        final AirlineData airline = serializer.fromJson(AIRLINE_JSON, AirlineData.class);

        assertNotNull(airline);

        assertNotNull(airline.getTicket());
        assertEquals("045-21351455613", airline.getTicket().getNumber());
        assertEquals(LocalDate.of(2023, 5, 20), airline.getTicket().getIssueDate());
        assertEquals("AI", airline.getTicket().getIssuingCarrierCode());
        // travel_package_indicator was missing from the class entirely.
        assertEquals("B", airline.getTicket().getTravelPackageIndicator());
        assertEquals("World Tours", airline.getTicket().getTravelAgencyName());
        assertEquals("01", airline.getTicket().getTravelAgencyCode());

        assertNotNull(airline.getPassenger());
        assertEquals(1, airline.getPassenger().size());
        assertEquals("John", airline.getPassenger().get(0).getFirstName());
        assertEquals("White", airline.getPassenger().get(0).getLastName());
        assertEquals(LocalDate.of(1990, 5, 26), airline.getPassenger().get(0).getDateOfBirth());
        assertNotNull(airline.getPassenger().get(0).getAddress());
        assertEquals(CountryCode.US, airline.getPassenger().get(0).getAddress().getCountry());

        assertNotNull(airline.getFlightLegDetails());
        assertEquals(1, airline.getFlightLegDetails().size());

        final FlightLegDetails leg = airline.getFlightLegDetails().get(0);
        // flight_number is a string in the spec, not an integer.
        assertEquals("101", leg.getFlightNumber());
        assertEquals("BA", leg.getCarrierCode());
        // class_of_travelling, double l. The SDK used to ship service_class.
        assertEquals("J", leg.getClassOfTravelling());
        assertEquals("LHR", leg.getDepartureAirport());
        assertEquals(LocalDate.of(2023, 6, 19), leg.getDepartureDate());
        assertEquals("15:30", leg.getDepartureTime());
        assertEquals("LAX", leg.getArrivalAirport());
        // stop_over_code, three tokens. The SDK used to ship stopover_code.
        assertEquals("x", leg.getStopOverCode());
        assertEquals("SPRSVR", leg.getFareBasisCode());
    }

    /**
     * The same fixture with passenger replaced by its first element, unchanged. This is the shape
     * PayPal sends; the spec allows it on PaymentInterfacesProcessingAirlineData with the note
     * "PayPal requires a single object".
     */
    @Test
    void shouldDeserializeAirlineDataWithPassengerAsASingleObject() {
        final String json = "{"
                + "\"ticket\":{\"number\":\"045-21351455613\"},"
                + "\"passenger\":{"
                + "\"first_name\":\"John\","
                + "\"last_name\":\"White\","
                + "\"date_of_birth\":\"1990-05-26\","
                + "\"address\":{\"country\":\"US\"}"
                + "}"
                + "}";

        final AirlineData airline = serializer.fromJson(json, AirlineData.class);

        // Normalized to a one-element list, so callers only handle one shape.
        assertNotNull(airline.getPassenger());
        assertEquals(1, airline.getPassenger().size());
        assertEquals("John", airline.getPassenger().get(0).getFirstName());
        assertEquals("White", airline.getPassenger().get(0).getLastName());
        assertEquals(LocalDate.of(1990, 5, 26), airline.getPassenger().get(0).getDateOfBirth());
        assertEquals(CountryCode.US, airline.getPassenger().get(0).getAddress().getCountry());
    }

    @Test
    void shouldDeserializeAirlineDataWithNoPassengerAtAll() {
        final String json = "{\"ticket\":{\"number\":\"045\"},\"passenger\":null}";

        final AirlineData airline = serializer.fromJson(json, AirlineData.class);

        assertNotNull(airline.getTicket());
        assertNull(airline.getPassenger());
    }

    @Test
    void shouldDeserializeMultiplePassengers() {
        final String json = "{\"passenger\":["
                + "{\"first_name\":\"John\"},"
                + "{\"first_name\":\"Jane\"}"
                + "]}";

        final AirlineData airline = serializer.fromJson(json, AirlineData.class);

        assertEquals(2, airline.getPassenger().size());
        assertEquals("John", airline.getPassenger().get(0).getFirstName());
        assertEquals("Jane", airline.getPassenger().get(1).getFirstName());
    }

    /**
     * Pins the outbound cardinality against the live API: an object is accepted on every request
     * surface, an array only on POST /payments. Hosted payments, payment links and payment
     * contexts all reject the array form, and ProcessingSettings is shared with hosted payments
     * and payment links, so "always an array" would break them. See
     * GsonSerializer.singleOrArrayPassengerFactory for the sandbox-verified matrix.
     */
    @Test
    void shouldSerializeASinglePassengerAsAnObject() {
        final AirlineData airline = AirlineData.builder()
                .passenger(Collections.singletonList(
                        Passenger.builder().firstName("John").lastName("White").build()))
                .build();

        final String json = serializer.toJson(airline);

        assertTrue(json.contains("\"passenger\":{"), json);
        assertFalse(json.contains("\"passenger\":["), json);
    }

    @Test
    void shouldSerializeSeveralPassengersAsAnArray() {
        final AirlineData airline = AirlineData.builder()
                .passenger(Arrays.asList(
                        Passenger.builder().firstName("John").build(),
                        Passenger.builder().firstName("Jane").build()))
                .build();

        assertTrue(serializer.toJson(airline).contains("\"passenger\":[{"));
    }

    /**
     * An empty array and an explicit null are both rejected with
     * processing_airline_data_0_passenger_invalid, so the member has to be absent.
     */
    @Test
    void shouldOmitPassengerWhenThereAreNone() {
        assertFalse(serializer.toJson(AirlineData.builder()
                .ticket(Ticket.builder().number("045").build())
                .passenger(Collections.<Passenger>emptyList())
                .build()).contains("passenger"));

        assertFalse(serializer.toJson(AirlineData.builder()
                .ticket(Ticket.builder().number("045").build())
                .build()).contains("passenger"));
    }

    @Test
    void shouldRoundTripSerializeAirlineData() {
        final AirlineData original = AirlineData.builder()
                .ticket(Ticket.builder()
                        .number("045-21351455613")
                        .issueDate(LocalDate.of(2023, 5, 20))
                        .travelPackageIndicator("B")
                        .build())
                .passenger(Arrays.asList(
                        Passenger.builder()
                                .firstName("John")
                                .dateOfBirth(LocalDate.of(1990, 5, 26))
                                .build(),
                        Passenger.builder().firstName("Jane").build()))
                .flightLegDetails(Collections.singletonList(
                        FlightLegDetails.builder()
                                .flightNumber("101")
                                .classOfTravelling("J")
                                .stopOverCode("x")
                                .departureDate(LocalDate.of(2023, 6, 19))
                                .build()))
                .build();

        final AirlineData result = serializer.fromJson(serializer.toJson(original), AirlineData.class);

        assertEquals("045-21351455613", result.getTicket().getNumber());
        assertEquals(LocalDate.of(2023, 5, 20), result.getTicket().getIssueDate());
        assertEquals("B", result.getTicket().getTravelPackageIndicator());
        assertEquals(2, result.getPassenger().size());
        assertEquals("John", result.getPassenger().get(0).getFirstName());
        assertEquals(LocalDate.of(1990, 5, 26), result.getPassenger().get(0).getDateOfBirth());
        assertEquals("Jane", result.getPassenger().get(1).getFirstName());
        assertEquals("101", result.getFlightLegDetails().get(0).getFlightNumber());
        assertEquals("J", result.getFlightLegDetails().get(0).getClassOfTravelling());
        assertEquals("x", result.getFlightLegDetails().get(0).getStopOverCode());
        assertEquals(LocalDate.of(2023, 6, 19), result.getFlightLegDetails().get(0).getDepartureDate());
    }

    /**
     * Asserts on the serialized string, so a future rename cannot pass silently. These keys were
     * all wrong at some point and each one was dropped by the gateway.
     */
    @Test
    void shouldSerializeAirlineKeysExactlyAsTheSpecNamesThem() {
        final AirlineData airline = AirlineData.builder()
                .ticket(Ticket.builder()
                        .issueDate(LocalDate.of(2023, 5, 20))
                        .travelPackageIndicator("B")
                        .build())
                .passenger(Collections.singletonList(Passenger.builder().firstName("John").build()))
                .flightLegDetails(Collections.singletonList(
                        FlightLegDetails.builder()
                                .flightNumber("101")
                                .classOfTravelling("J")
                                .stopOverCode("x")
                                .departureDate(LocalDate.of(2023, 6, 19))
                                .build()))
                .build();

        final String json = serializer.toJson(airline);

        assertTrue(json.contains("\"class_of_travelling\":\"J\""), json);
        assertTrue(json.contains("\"stop_over_code\":\"x\""), json);
        assertTrue(json.contains("\"flight_number\":\"101\""), json);
        assertTrue(json.contains("\"issue_date\":\"2023-05-20\""), json);
        assertTrue(json.contains("\"travel_package_indicator\":\"B\""), json);
        assertTrue(json.contains("\"departure_date\":\"2023-06-19\""), json);

        // Keys the API does not define.
        assertFalse(json.contains("service_class"), json);
        assertFalse(json.contains("\"stopover_code\""), json);
    }

    /**
     * stopoverCode was removed, so stopover_code is now unreachable: no caller can put that key
     * on the wire. serviceClass is retained but deprecated.
     */
    @Test
    void shouldNotExposeTheRemovedStopoverCodeField() throws Exception {
        assertNotNull(FlightLegDetails.class.getDeclaredField("stopOverCode"));
        assertNotNull(FlightLegDetails.class.getDeclaredField("serviceClass"));

        boolean removed = false;
        try {
            FlightLegDetails.class.getDeclaredField("stopoverCode");
        } catch (final NoSuchFieldException expected) {
            removed = true;
        }
        assertTrue(removed, "stopoverCode should no longer exist on FlightLegDetails");
    }
}
