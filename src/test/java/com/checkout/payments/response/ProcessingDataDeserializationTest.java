package com.checkout.payments.response;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Deserialization tests for {@link ProcessingData}, focused on fields recently aligned with
 * the Checkout.com swagger spec (scheme, partner_fraud_status, partner_merchant_advice_code,
 * accommodation_data, airline_data, fallback_source_used, failure_code, partner_code,
 * partner_response_code).
 */
class ProcessingDataDeserializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeScheme() {
        final String json = "{\"scheme\":\"ACCEL\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("ACCEL", data.getScheme());
    }

    @Test
    void shouldDeserializePartnerFraudStatus() {
        final String json = "{\"partner_fraud_status\":\"Pending\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("Pending", data.getPartnerFraudStatus());
    }

    @Test
    void shouldDeserializePartnerMerchantAdviceCode() {
        final String json = "{\"partner_merchant_advice_code\":\"24\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("24", data.getPartnerMerchantAdviceCode());
    }

    @Test
    void shouldDeserializeAccommodationData() {
        final String json = "{\"accommodation_data\":[{\"name\":\"Grand Hotel\"}]}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertNotNull(data.getAccommodationData());
        assertEquals(1, data.getAccommodationData().size());
        assertEquals("Grand Hotel", data.getAccommodationData().get(0).getName());
    }

    @Test
    void shouldDeserializeAirlineData() {
        final String json = "{\"airline_data\":[{\"ticket\":{\"number\":\"045-21351455613\"}}]}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertNotNull(data.getAirlineData());
        assertEquals(1, data.getAirlineData().size());
        assertNotNull(data.getAirlineData().get(0).getTicket());
        assertEquals("045-21351455613", data.getAirlineData().get(0).getTicket().getNumber());
    }

    @Test
    void shouldDeserializeFallbackSourceUsed() {
        final String json = "{\"fallback_source_used\":true}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals(Boolean.TRUE, data.getFallbackSourceUsed());
    }

    @Test
    void shouldDeserializeFailureCode() {
        final String json = "{\"failure_code\":\"partner_error\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("partner_error", data.getFailureCode());
    }

    @Test
    void shouldDeserializePartnerCode() {
        final String json = "{\"partner_code\":\"999111\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("999111", data.getPartnerCode());
    }

    @Test
    void shouldDeserializePartnerResponseCode() {
        final String json = "{\"partner_response_code\":\"ER_WRONG_TICKET\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("ER_WRONG_TICKET", data.getPartnerResponseCode());
    }

    @Test
    void shouldDeserializeSchemeTransactionLinkId() {
        final String json = "{\"scheme_transaction_link_id\":\"MTL-001\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("MTL-001", data.getSchemeTransactionLinkId());
    }

    @Test
    void shouldDeserializeAllNewFieldsTogether() {
        final String json = "{"
                + "\"scheme\":\"VISA\","
                + "\"partner_fraud_status\":\"Accepted\","
                + "\"partner_merchant_advice_code\":\"24\","
                + "\"fallback_source_used\":false,"
                + "\"failure_code\":\"partner_error\","
                + "\"partner_code\":\"902111\","
                + "\"partner_response_code\":\"DECLINED\","
                + "\"scheme_transaction_link_id\":\"MTL-XYZ-789\""
                + "}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("VISA", data.getScheme());
        assertEquals("Accepted", data.getPartnerFraudStatus());
        assertEquals("24", data.getPartnerMerchantAdviceCode());
        assertEquals(Boolean.FALSE, data.getFallbackSourceUsed());
        assertEquals("partner_error", data.getFailureCode());
        assertEquals("902111", data.getPartnerCode());
        assertEquals("DECLINED", data.getPartnerResponseCode());
        assertEquals("MTL-XYZ-789", data.getSchemeTransactionLinkId());
    }

    @Test
    void shouldLeaveNewFieldsNullWhenAbsent() {
        final String json = "{\"locale\":\"en-GB\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("en-GB", data.getLocale());
        assertNull(data.getScheme());
        assertNull(data.getPartnerFraudStatus());
        assertNull(data.getPartnerMerchantAdviceCode());
        assertNull(data.getAccommodationData());
        assertNull(data.getAirlineData());
        assertNull(data.getFallbackSourceUsed());
        assertNull(data.getFailureCode());
        assertNull(data.getPartnerCode());
        assertNull(data.getPartnerResponseCode());
        assertNull(data.getSchemeTransactionLinkId());
    }

    // ------------------------------------------------------------------------
    // airline_data / accommodation_data, full sub-tree
    //
    // shouldDeserializeAirlineData below only ever supplied {"ticket":{"number":...}}, so it
    // never touched passenger and stayed green through the whole life of the cardinality defect.
    // The fixture here is the swagger AirlineData / AccommodationData example values, shared
    // with the equivalent test in the other SDKs.
    // ------------------------------------------------------------------------

    private static final String AIRLINE_AND_ACCOMMODATION_JSON = "{"
            + "\"airline_data\":[{"
            + "\"ticket\":{"
            + "\"number\":\"045-21351455613\","
            + "\"issue_date\":\"2023-05-20\","
            + "\"issuing_carrier_code\":\"AI\","
            + "\"travel_package_indicator\":\"B\","
            + "\"travel_agency_name\":\"World Tours\","
            + "\"travel_agency_code\":\"01\""
            + "},"
            + "\"passenger\":[{"
            + "\"first_name\":\"John\",\"last_name\":\"White\","
            + "\"date_of_birth\":\"1990-05-26\",\"address\":{\"country\":\"US\"}"
            + "}],"
            + "\"flight_leg_details\":[{"
            + "\"flight_number\":\"101\",\"carrier_code\":\"BA\","
            + "\"class_of_travelling\":\"J\",\"departure_airport\":\"LHR\","
            + "\"departure_date\":\"2023-06-19\",\"departure_time\":\"15:30\","
            + "\"arrival_airport\":\"LAX\",\"stop_over_code\":\"x\","
            + "\"fare_basis_code\":\"SPRSVR\""
            + "}]"
            + "}],"
            + "\"accommodation_data\":[{"
            + "\"name\":\"The Sea View Hotel\","
            + "\"booking_reference\":\"HOTEL123\","
            + "\"check_in_date\":\"2023-06-20\","
            + "\"check_out_date\":\"2023-06-23\","
            + "\"address\":{\"address_line1\":\"123 Beach Road\",\"zip\":\"10001\"},"
            + "\"state\":\"FL\",\"country\":\"USA\",\"city\":\"Los Angeles\","
            + "\"number_of_rooms\":2,"
            + "\"guests\":[{\"first_name\":\"Jane\",\"last_name\":\"Doe\","
            + "\"date_of_birth\":\"1985-07-14\"}],"
            + "\"room\":[{\"rate\":\"70\",\"number_of_nights_at_room_rate\":\"3\"}],"
            + "\"property_phone\":[{\"country_code\":\"44\",\"number\":\"7123456789\"}],"
            + "\"customer_service_phone\":[{\"country_code\":\"44\",\"number\":\"7987654321\"}]"
            + "}]"
            + "}";

    @Test
    void shouldDeserializeAirlineDataWithPassengerAsAnArray() {
        final ProcessingData data =
                serializer.fromJson(AIRLINE_AND_ACCOMMODATION_JSON, ProcessingData.class);

        assertNotNull(data.getAirlineData());
        assertEquals(1, data.getAirlineData().size());

        assertEquals("045-21351455613", data.getAirlineData().get(0).getTicket().getNumber());
        assertEquals(LocalDate.of(2023, 5, 20),
                data.getAirlineData().get(0).getTicket().getIssueDate());
        assertEquals("B", data.getAirlineData().get(0).getTicket().getTravelPackageIndicator());

        assertNotNull(data.getAirlineData().get(0).getPassenger());
        assertEquals(1, data.getAirlineData().get(0).getPassenger().size());
        assertEquals("John", data.getAirlineData().get(0).getPassenger().get(0).getFirstName());
        assertEquals(LocalDate.of(1990, 5, 26),
                data.getAirlineData().get(0).getPassenger().get(0).getDateOfBirth());
        assertEquals(CountryCode.US,
                data.getAirlineData().get(0).getPassenger().get(0).getAddress().getCountry());

        assertEquals("101",
                data.getAirlineData().get(0).getFlightLegDetails().get(0).getFlightNumber());
        assertEquals("J",
                data.getAirlineData().get(0).getFlightLegDetails().get(0).getClassOfTravelling());
        assertEquals("x",
                data.getAirlineData().get(0).getFlightLegDetails().get(0).getStopOverCode());
        assertEquals(LocalDate.of(2023, 6, 19),
                data.getAirlineData().get(0).getFlightLegDetails().get(0).getDepartureDate());
    }

    @Test
    void shouldDeserializeAccommodationDataFromTheSameFixture() {
        final ProcessingData data =
                serializer.fromJson(AIRLINE_AND_ACCOMMODATION_JSON, ProcessingData.class);

        assertNotNull(data.getAccommodationData());
        assertEquals(1, data.getAccommodationData().size());

        assertEquals("The Sea View Hotel", data.getAccommodationData().get(0).getName());
        assertEquals("HOTEL123", data.getAccommodationData().get(0).getBookingReference());
        assertEquals(LocalDate.of(2023, 6, 20), data.getAccommodationData().get(0).getCheckInDate());
        assertEquals(LocalDate.of(2023, 6, 23), data.getAccommodationData().get(0).getCheckOutDate());
        assertEquals("123 Beach Road",
                data.getAccommodationData().get(0).getAddress().getAddressLine1());
        assertEquals("Los Angeles", data.getAccommodationData().get(0).getCity());
        assertEquals(2, data.getAccommodationData().get(0).getNumberOfRooms());

        // state and country are free-form strings, not country codes.
        assertEquals("FL", data.getAccommodationData().get(0).getState());
        assertEquals("USA", data.getAccommodationData().get(0).getCountry());

        assertEquals("Jane", data.getAccommodationData().get(0).getGuests().get(0).getFirstName());
        assertEquals(LocalDate.of(1985, 7, 14),
                data.getAccommodationData().get(0).getGuests().get(0).getDateOfBirth());
        assertEquals("70", data.getAccommodationData().get(0).getRoom().get(0).getRate());
        assertEquals("3",
                data.getAccommodationData().get(0).getRoom().get(0).getNumberOfNightsAtRoomRate());
        assertEquals("7123456789",
                data.getAccommodationData().get(0).getPropertyPhone().get(0).getNumber());
        assertEquals("7987654321",
                data.getAccommodationData().get(0).getCustomerServicePhone().get(0).getNumber());
    }

    /**
     * Regression: this used to fail when passenger came back as an array in SDKs that typed it as
     * a single object, and it still cannot be exercised unless the field is populated. Reported
     * internally.
     */
    @Test
    void shouldDeserializeAirlinePassengerFromASingleObjectOnProcessingData() {
        final String json = "{\"airline_data\":[{"
                + "\"ticket\":{\"number\":\"045\"},"
                + "\"passenger\":{\"first_name\":\"John\",\"date_of_birth\":\"1990-05-26\"}"
                + "}]}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertEquals(1, data.getAirlineData().get(0).getPassenger().size());
        assertEquals("John", data.getAirlineData().get(0).getPassenger().get(0).getFirstName());
        assertEquals(LocalDate.of(1990, 5, 26),
                data.getAirlineData().get(0).getPassenger().get(0).getDateOfBirth());
    }

}
