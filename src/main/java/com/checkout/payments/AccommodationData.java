package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationData {

    /**
     * The name of the accommodation property.
     * [Optional]
     */
    private String name;

    /**
     * The booking reference for this accommodation.
     * [Optional]
     */
    @SerializedName("booking_reference")
    private String bookingReference;

    /**
     * The check-in date.
     * [Optional]
     * Format: date (yyyy-MM-dd)
     */
    @SerializedName("check_in_date")
    private LocalDate checkInDate;

    /**
     * The check-out date.
     * [Optional]
     * Format: date (yyyy-MM-dd)
     */
    @SerializedName("check_out_date")
    private LocalDate checkOutDate;

    /**
     * The address of the accommodation property.
     * [Optional]
     */
    private Address address;

    /**
     * The state or region code where the property is located, as an ISO 3166-1 alpha-2 code.
     * [Optional]
     */
    private CountryCode state;

    /**
     * The country where the property is located, as an ISO 3166-1 alpha-2 code.
     * [Optional]
     */
    private CountryCode country;

    /**
     * The city where the property is located.
     * [Optional]
     */
    private String city;

    /**
     * The number of rooms booked.
     * [Optional]
     */
    @SerializedName("number_of_rooms")
    private Integer numberOfRooms;

    /**
     * The list of guests for this booking.
     * [Optional]
     */
    private List<AccommodationGuest> guests;

    /**
     * The room details for this booking.
     * [Optional]
     */
    private List<AccommodationRoom> room;

    /**
     * The property's phone numbers.
     * [Optional]
     */
    @SerializedName("property_phone")
    private List<Phone> propertyPhone;

    /**
     * The customer service phone numbers for the property.
     * [Optional]
     */
    @SerializedName("customer_service_phone")
    private List<Phone> customerServicePhone;

}
