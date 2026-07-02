package com.checkout.payments.contexts;

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
    private String flightNumber;

    /**
     * The carrier code.
     * [Optional]
     */
    private String carrierCode;

    /**
     * The class of travelling.
     * [Optional]
     */
    private String classOfTravelling;

    /**
     * The departure airport.
     * [Optional]
     */
    private String departureAirport;

    /**
     * The date of the scheduled take off.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate departureDate;

    /**
     * The departure time.
     * [Optional]
     */
    private String departureTime;

    /**
     * The arrival airport.
     * [Optional]
     */
    private String arrivalAirport;

    /**
     * A one-letter code indicating stopover entitlement: space, O (entitled), or X (not entitled).
     * [Optional]
     */
    private String stopOverCode;

    /**
     * The fare basis code, alphanumeric.
     * [Optional]
     */
    private String fareBasisCode;
}
