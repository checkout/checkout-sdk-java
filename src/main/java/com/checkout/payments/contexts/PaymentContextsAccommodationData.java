package com.checkout.payments.contexts;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
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
public final class PaymentContextsAccommodationData {

    private String name;

    @SerializedName("booking_reference")
    private String bookingReference;

    @SerializedName("check_in_date")
    private Instant checkInDate;

    @SerializedName("check_out_date")
    private Instant checkOutDate;

    private Address address;

    private CountryCode state;

    private CountryCode country;

    private String city;

    @SerializedName("number_of_rooms")
    private Integer numberOfRooms;

    @SerializedName("guests")
    private List<PaymentContextsGuests> guests;

    @SerializedName("room")
    private List<PaymentContextsAccommodationRoom> room;
}
