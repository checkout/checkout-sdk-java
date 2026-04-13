package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsFlightLegDetails {

    /**
     * The flight number.
     * [Optional]
     */
    @SerializedName("flight_number")
    private String flightNumber;

    /**
     * The carrier code.
     * [Optional]
     */
    @SerializedName("carrier_code")
    private String carrierCode;

    /**
     * The class of travelling.
     * [Optional]
     */
    @SerializedName("class_of_travelling")
    private String classOfTravelling;

    /**
     * The departure airport.
     * [Optional]
     */
    @SerializedName("departure_airport")
    private String departureAirport;

    /**
     * The date of the scheduled take off.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("departure_date")
    private LocalDate departureDate;

    /**
     * The departure time.
     * [Optional]
     */
    @SerializedName("departure_time")
    private String departureTime;

    /**
     * The arrival airport.
     * [Optional]
     */
    @SerializedName("arrival_airport")
    private String arrivalAirport;

    /**
     * A one-letter code indicating stopover entitlement: space, O (entitled), or X (not entitled).
     * [Optional]
     */
    @SerializedName("stop_over_code")
    private String stopOverCode;

    /**
     * The fare basis code, alphanumeric.
     * [Optional]
     */
    @SerializedName("fare_basis_code")
    private String fareBasisCode;
}
