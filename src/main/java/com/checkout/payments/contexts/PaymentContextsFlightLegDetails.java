package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsFlightLegDetails {

    @SerializedName("flight_number")
    private String flightNumber;

    @SerializedName("carrier_code")
    private String carrierCode;

    @SerializedName("class_of_travelling")
    private String classOfTravelling;

    @SerializedName("departure_airport")
    private String departureAirport;

    @SerializedName("departure_date")
    private Instant departureDate;

    @SerializedName("departure_time")
    private String departureTime;

    @SerializedName("arrival_airport")
    private String arrivalAirport;

    @SerializedName("stop_over_code")
    private String stopOverCode;

    @SerializedName("fare_basis_code")
    private String fareBasisCode;
}
