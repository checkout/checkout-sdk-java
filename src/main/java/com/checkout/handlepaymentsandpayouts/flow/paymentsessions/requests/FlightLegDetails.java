package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

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
public final class FlightLegDetails {

    /**
     * The flight identifier.
     */
    @SerializedName("flight_number")
    private String flightNumber;

    /**
     * The IATA 2-letter accounting code (PAX) that identifies the carrier. This field is required if the airline data
     * includes leg details.
     */
    @SerializedName("carrier_code")
    private String carrierCode;

    /**
     * A one-letter travel class identifier. The following are common: F = First class, J = Business class, Y = Economy
     * class, W = Premium economy.
     */
    @SerializedName("class_of_travelling")
    private String classOfTravelling;

    /**
     * The IATA three-letter airport code of the departure airport. This field is required if the airline data includes
     * leg details.
     */
    @SerializedName("departure_airport")
    private String departureAirport;

    /**
     * The date of the scheduled take off.
     */
    @SerializedName("departure_date")
    private Instant departureDate;

    /**
     * The time of the scheduled take off.
     */
    @SerializedName("departure_time")
    private String departureTime;

    /**
     * The IATA 3-letter airport code of the destination airport. This field is required if the airline data includes
     * leg details.
     */
    @SerializedName("arrival_airport")
    private String arrivalAirport;

    /**
     * A one-letter code that indicates whether the passenger is entitled to make a stopover. Can be a space, O if the
     * passenger is entitled to make a stopover, or X if they are not.
     */
    @SerializedName("stop_over_code")
    private String stopOverCode;

    /**
     * The fare basis code, alphanumeric.
     */
    @SerializedName("fare_basis_code")
    private String fareBasisCode;

}
