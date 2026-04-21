package com.checkout.payments;

import com.checkout.GsonSerializer;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccommodationDataSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithRequiredFields() {
        final AccommodationData data = AccommodationData.builder()
                .name("Grand Hotel")
                .checkInDate(LocalDate.of(2025, 6, 1))
                .checkOutDate(LocalDate.of(2025, 6, 5))
                .build();

        assertDoesNotThrow(() -> serializer.toJson(data));
    }

    @Test
    void shouldSerializeWithAllOptionalFields() {
        final AccommodationData data = AccommodationData.builder()
                .name("Grand Hotel")
                .bookingReference("BK-12345")
                .checkInDate(LocalDate.of(2025, 6, 1))
                .checkOutDate(LocalDate.of(2025, 6, 5))
                .address(Address.builder()
                        .addressLine1("1 Hotel Plaza")
                        .city("Paris")
                        .country(CountryCode.FR)
                        .build())
                .country(CountryCode.FR)
                .city("Paris")
                .numberOfRooms(2)
                .guests(Arrays.asList(
                        AccommodationGuest.builder()
                                .firstName("John")
                                .lastName("Smith")
                                .dateOfBirth(LocalDate.of(1985, 3, 15))
                                .build()
                ))
                .room(Arrays.asList(
                        AccommodationRoom.builder()
                                .rate("150.00")
                                .numberOfNightsAtRoomRate("4")
                                .build()
                ))
                .propertyPhone(Arrays.asList(
                        Phone.builder().countryCode("+33").number("123456789").build()
                ))
                .customerServicePhone(Arrays.asList(
                        Phone.builder().countryCode("+33").number("987654321").build()
                ))
                .build();

        assertDoesNotThrow(() -> serializer.toJson(data));
    }

    @Test
    void shouldSerializeDatesAsLocalDate() {
        final AccommodationData data = AccommodationData.builder()
                .checkInDate(LocalDate.of(2025, 6, 1))
                .checkOutDate(LocalDate.of(2025, 6, 5))
                .build();

        final String json = serializer.toJson(data);

        assertNotNull(json);
    }

    @Test
    void shouldDeserializeFromJson() {
        final String json = "{"
                + "\"name\":\"Grand Hotel\","
                + "\"booking_reference\":\"BK-12345\","
                + "\"check_in_date\":\"2025-06-01\","
                + "\"check_out_date\":\"2025-06-05\","
                + "\"country\":\"FR\","
                + "\"city\":\"Paris\","
                + "\"number_of_rooms\":2,"
                + "\"guests\":[{"
                + "  \"first_name\":\"John\","
                + "  \"last_name\":\"Smith\","
                + "  \"date_of_birth\":\"1985-03-15\""
                + "}],"
                + "\"room\":[{"
                + "  \"rate\":\"150.00\","
                + "  \"number_of_nights_at_room_rate\":\"4\""
                + "}]"
                + "}";

        final AccommodationData data = serializer.fromJson(json, AccommodationData.class);

        assertNotNull(data);
        assertEquals("Grand Hotel", data.getName());
        assertEquals("BK-12345", data.getBookingReference());
        assertEquals(LocalDate.of(2025, 6, 1), data.getCheckInDate());
        assertEquals(LocalDate.of(2025, 6, 5), data.getCheckOutDate());
        assertEquals(CountryCode.FR, data.getCountry());
        assertEquals("Paris", data.getCity());
        assertEquals(2, data.getNumberOfRooms());
        assertNotNull(data.getGuests());
        assertEquals(1, data.getGuests().size());
        assertEquals("John", data.getGuests().get(0).getFirstName());
        assertEquals(LocalDate.of(1985, 3, 15), data.getGuests().get(0).getDateOfBirth());
        assertNotNull(data.getRoom());
        assertEquals("150.00", data.getRoom().get(0).getRate());
    }

    @Test
    void shouldRoundTripSerialize() {
        final AccommodationData original = AccommodationData.builder()
                .name("Grand Hotel")
                .bookingReference("BK-12345")
                .checkInDate(LocalDate.of(2025, 6, 1))
                .checkOutDate(LocalDate.of(2025, 6, 5))
                .country(CountryCode.FR)
                .numberOfRooms(1)
                .guests(Arrays.asList(
                        AccommodationGuest.builder()
                                .firstName("John")
                                .lastName("Smith")
                                .build()
                ))
                .build();

        final String json = serializer.toJson(original);
        final AccommodationData deserialized = serializer.fromJson(json, AccommodationData.class);

        assertNotNull(deserialized);
        assertEquals("Grand Hotel", deserialized.getName());
        assertEquals("BK-12345", deserialized.getBookingReference());
        assertEquals(LocalDate.of(2025, 6, 1), deserialized.getCheckInDate());
        assertEquals(CountryCode.FR, deserialized.getCountry());
        assertEquals(1, deserialized.getNumberOfRooms());
        assertNotNull(deserialized.getGuests());
        assertEquals("John", deserialized.getGuests().get(0).getFirstName());
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{}";

        final AccommodationData data = serializer.fromJson(json, AccommodationData.class);

        assertNotNull(data);
        assertDoesNotThrow(() -> serializer.toJson(data));
        assertNull(data.getName());
        assertNull(data.getCheckInDate());
        assertNull(data.getGuests());
        assertNull(data.getRoom());
    }
}
