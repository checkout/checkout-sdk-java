package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Contains information about a flight leg booked by the customer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FlightLegDetails {

    /**
     * The flight identifier.
     * [Optional]
     */
    private String flightNumber;

    /**
     * The IATA 2-letter accounting code (PAX) that identifies the carrier.
     * This field is required if the airline data includes leg details.
     * [Optional]
     */
    private String carrierCode;

    /**
     * A one-letter travel class identifier. The following are common:
     * F = First class, J = Business class, Y = Economy class, W = Premium economy.
     * [Optional]
     */
    private String classOfTravelling;

    /**
     * The IATA three-letter airport code of the departure airport.
     * This field is required if the airline data includes leg details.
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
     * The time of the scheduled take off.
     * [Optional]
     */
    private String departureTime;

    /**
     * The IATA 3-letter airport code of the destination airport.
     * This field is required if the airline data includes leg details.
     * [Optional]
     */
    private String arrivalAirport;

    /**
     * A one-letter code that indicates whether the passenger is entitled to make a stopover.
     * Can be a space, O if the passenger is entitled to make a stopover, or X if they are not.
     * [Optional]
     */
    private String stopOverCode;

    /**
     * The fare basis code, alphanumeric.
     * [Optional]
     */
    private String fareBasisCode;

    /**
     * Not in the current spec, will be removed in a future version.
     * Serializes as {@code service_class}, which the API does not define, so the value is
     * discarded by the gateway. Use {@link #getClassOfTravelling()} instead, which maps the
     * spec property {@code class_of_travelling}.
     *
     * @deprecated Not defined by the API, the gateway discards it. Use
     * {@code classOfTravelling}, which maps {@code class_of_travelling}.
     */
    @Deprecated
    private String serviceClass;

}
