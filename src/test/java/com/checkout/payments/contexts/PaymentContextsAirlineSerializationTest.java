package com.checkout.payments.contexts;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.payments.PassengerAddress;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Serialization tests for the payment contexts airline and accommodation sub-tree.
 *
 * <p>Covers the cardinality of airline_data[].ticket (a single object) and
 * airline_data[].passenger (an array that also accepts a bare object), and the two nested
 * shapes whose types disagreed with the spec: accommodation state/country were typed as the
 * CountryCode enum where the spec declares free-form strings, and
 * room[].number_of_nights_at_room_rate was an integer where the spec declares a string.
 */
class PaymentContextsAirlineSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeAirlineTicketAsAnObjectAndPassengerAsAnArray() {
        final PaymentContextsAirlineData airline = PaymentContextsAirlineData.builder()
                .ticket(PaymentContextsTicket.builder()
                        .number("045-21351455613")
                        .issueDate(LocalDate.of(2023, 5, 20))
                        .build())
                .passenger(Collections.singletonList(
                        PaymentContextsPassenger.builder().firstName("John").build()))
                .flightLegDetails(Collections.singletonList(
                        PaymentContextsFlightLegDetails.builder()
                                .flightNumber("101")
                                .classOfTravelling("J")
                                .stopOverCode("x")
                                .build()))
                .build();

        final String json = serializer.toJson(airline);

        assertTrue(json.contains("\"ticket\":{"), json);
        assertFalse(json.contains("\"ticket\":["), json);
        assertTrue(json.contains("\"passenger\":[{"), json);
        assertTrue(json.contains("\"class_of_travelling\":\"J\""), json);
        assertTrue(json.contains("\"stop_over_code\":\"x\""), json);
        assertTrue(json.contains("\"flight_number\":\"101\""), json);
    }

    @Test
    void shouldDeserializeAirlineTicketFromAnObject() {
        final String json = "{"
                + "\"ticket\":{\"number\":\"045-21351455613\",\"issue_date\":\"2023-05-20\"},"
                + "\"passenger\":[{\"first_name\":\"John\"}]"
                + "}";

        final PaymentContextsAirlineData airline =
                serializer.fromJson(json, PaymentContextsAirlineData.class);

        assertNotNull(airline.getTicket());
        assertEquals("045-21351455613", airline.getTicket().getNumber());
        assertEquals(LocalDate.of(2023, 5, 20), airline.getTicket().getIssueDate());
        assertEquals(1, airline.getPassenger().size());
        assertEquals("John", airline.getPassenger().get(0).getFirstName());
    }

    /**
     * PayPal is a payment contexts payment method and returns passenger as a bare object.
     */
    @Test
    void shouldDeserializeAirlinePassengerFromASingleObject() {
        final String json = "{"
                + "\"ticket\":{\"number\":\"045\"},"
                + "\"passenger\":{\"first_name\":\"John\",\"date_of_birth\":\"1990-05-26\","
                + "\"address\":{\"country\":\"US\"}}"
                + "}";

        final PaymentContextsAirlineData airline =
                serializer.fromJson(json, PaymentContextsAirlineData.class);

        assertNotNull(airline.getPassenger());
        assertEquals(1, airline.getPassenger().size());
        assertEquals("John", airline.getPassenger().get(0).getFirstName());
        assertEquals(LocalDate.of(1990, 5, 26), airline.getPassenger().get(0).getDateOfBirth());
        assertEquals(CountryCode.US, airline.getPassenger().get(0).getAddress().getCountry());
    }

    @Test
    void shouldRoundTripSerializeAirlineData() {
        final PaymentContextsAirlineData original = PaymentContextsAirlineData.builder()
                .ticket(PaymentContextsTicket.builder()
                        .number("045")
                        .travelPackageIndicator("B")
                        .build())
                .passenger(Arrays.asList(
                        PaymentContextsPassenger.builder()
                                .firstName("John")
                                .address(PassengerAddress.builder().country(CountryCode.GB).build())
                                .build(),
                        PaymentContextsPassenger.builder().firstName("Jane").build()))
                .build();

        final PaymentContextsAirlineData result =
                serializer.fromJson(serializer.toJson(original), PaymentContextsAirlineData.class);

        assertEquals("045", result.getTicket().getNumber());
        assertEquals("B", result.getTicket().getTravelPackageIndicator());
        assertEquals(2, result.getPassenger().size());
        assertEquals(CountryCode.GB, result.getPassenger().get(0).getAddress().getCountry());
        assertEquals("Jane", result.getPassenger().get(1).getFirstName());
    }

    /**
     * passenger.address defines exactly one property, country. It was the wider common Address,
     * whose other members the API does not read here.
     */
    @Test
    void shouldSerializeOnlyCountryOnThePassengerAddress() {
        final String json = serializer.toJson(PaymentContextsPassenger.builder()
                .firstName("John")
                .address(PassengerAddress.builder().country(CountryCode.US).build())
                .build());

        assertTrue(json.contains("\"address\":{\"country\":\"US\"}"), json);
        assertFalse(json.contains("address_line1"), json);
        assertFalse(json.contains("\"zip\""), json);
    }

    @Test
    void shouldSerializeNumberOfNightsAtRoomRateAsAString() {
        final String json = serializer.toJson(PaymentContextsAccommodationRoom.builder()
                .rate("70")
                .numberOfNightsAtRoomRate("3")
                .build());

        assertTrue(json.contains("\"rate\":\"70\""), json);
        assertTrue(json.contains("\"number_of_nights_at_room_rate\":\"3\""), json);
    }

    @Test
    void shouldDeserializeNumberOfNightsAtRoomRateFromAString() {
        final String json = "{\"rate\":\"70\",\"number_of_nights_at_room_rate\":\"3\"}";

        final PaymentContextsAccommodationRoom room =
                serializer.fromJson(json, PaymentContextsAccommodationRoom.class);

        assertEquals("70", room.getRate());
        assertEquals("3", room.getNumberOfNightsAtRoomRate());
    }

    /**
     * state and country are free-form strings. Typed as the CountryCode enum they could not carry
     * "FL", a US state, or "USA", a three-letter code.
     */
    @Test
    void shouldDeserializeAccommodationStateAndCountryAsFreeFormStrings() {
        final String json = "{"
                + "\"name\":\"The Sea View Hotel\","
                + "\"state\":\"FL\","
                + "\"country\":\"USA\","
                + "\"city\":\"Los Angeles\","
                + "\"room\":[{\"rate\":\"70\",\"number_of_nights_at_room_rate\":\"3\"}]"
                + "}";

        final PaymentContextsAccommodationData data =
                serializer.fromJson(json, PaymentContextsAccommodationData.class);

        assertEquals("The Sea View Hotel", data.getName());
        assertEquals("FL", data.getState());
        assertEquals("USA", data.getCountry());
        assertEquals("Los Angeles", data.getCity());
        assertEquals("3", data.getRoom().get(0).getNumberOfNightsAtRoomRate());
    }
}
