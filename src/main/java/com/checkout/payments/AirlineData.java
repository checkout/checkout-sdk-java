package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Contains information about the airline ticket and flights booked by the customer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AirlineData {

    /**
     * Contains information about the airline ticket.
     * [Optional]
     */
    private Ticket ticket;

    /**
     * Contains information about the passenger(s) on the flight.
     * [Optional]
     * <p>
     * The API returns this as an array. Some payment methods, PayPal among them, send a single
     * object instead, which the specification allows on the payment sessions, hosted payments
     * and payment links interfaces. Both shapes deserialize here; a single object becomes a
     * one-element list. Serialization always emits an array.
     */
    private List<Passenger> passenger;

    /**
     * Contains information about the flight leg(s) booked by the customer.
     * [Optional]
     */
    private List<FlightLegDetails> flightLegDetails;
}
