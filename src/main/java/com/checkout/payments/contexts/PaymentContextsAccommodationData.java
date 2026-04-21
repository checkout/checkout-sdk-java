package com.checkout.payments.contexts;

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
public final class PaymentContextsAccommodationData {

    /**
     * The name of the accommodation.
     * [Optional]
     */
    private String name;

    /**
     * The booking reference.
     * [Optional]
     */
    @SerializedName("booking_reference")
    private String bookingReference;

    /**
     * The actual or scheduled check-in date. For cruise: the cruise departure (sail) date.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("check_in_date")
    private LocalDate checkInDate;

    /**
     * The actual or scheduled check-out date. For cruise: the cruise return (sail end) date.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("check_out_date")
    private LocalDate checkOutDate;

    /**
     * The address of the accommodation.
     * [Optional]
     */
    private Address address;

    /**
     * The state of the accommodation.
     * [Optional]
     */
    private CountryCode state;

    /**
     * The country of the accommodation.
     * [Optional]
     */
    private CountryCode country;

    /**
     * The city of the accommodation.
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
     * Information about the guests staying at the accommodation.
     * [Optional]
     */
    @SerializedName("guests")
    private List<PaymentContextsGuests> guests;

    /**
     * Information about the rooms booked by the customer.
     * [Optional]
     */
    @SerializedName("room")
    private List<PaymentContextsAccommodationRoom> room;

    /**
     * The property's phone information.
     * [Optional]
     */
    @SerializedName("property_phone")
    private List<Phone> propertyPhone;

    /**
     * The customer service phone information.
     * [Optional]
     */
    @SerializedName("customer_service_phone")
    private List<Phone> customerServicePhone;
}
