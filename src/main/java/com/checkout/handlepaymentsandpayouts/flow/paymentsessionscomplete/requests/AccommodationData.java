package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationData {

    /**
     * For lodging, contains the lodging name that appears on the storefront/customer receipts. For cruise, contains the
     * ship name booked for the cruise.
     */
    private String name;

    /**
     * A unique identifier for the booking.
     */
    @SerializedName("booking_reference")
    private String bookingReference;

    /**
     * For lodging, contains the actual or scheduled date the guest checked-in. For cruise, contains the cruise
     * departure date also known as sail date.
     */
    @SerializedName("check_in_date")
    private Instant checkInDate;

    /**
     * For lodging, contains the actual or scheduled date the guest checked-out. For cruise, contains the cruise return
     * date also known as sail end date.
     */
    @SerializedName("check_out_date")
    private Instant checkOutDate;

    /**
     * The address details of the accommodation.
     */
    private Address address;

    /**
     * The state or province of the address country (ISO 3166-2 code of up to two alphanumeric characters).
     */
    private String state;

    /**
     * The ISO country code of the address.
     */
    private String country;

    /**
     * The address city.
     */
    private String city;

    /**
     * The total number of rooms booked for the accommodation.
     */
    @SerializedName("number_of_rooms")
    private Long numberOfRooms;

    /**
     * Contains information about the guests staying at the accommodation.
     */
    private List<Guest> guests;

    /**
     * Contains information about the rooms booked by the customer.
     */
    private List<Room> room;

}
