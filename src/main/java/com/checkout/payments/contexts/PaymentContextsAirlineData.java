package com.checkout.payments.contexts;

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
public final class PaymentContextsAirlineData {

    /**
     * Contains information about the airline ticket.
     * [Optional]
     */
    private PaymentContextsTicket ticket;

    /**
     * Contains information about the passenger(s) on the flight.
     * [Optional]
     * <p>
     * Deserialization also accepts a single object, which PayPal returns in place of an array;
     * it becomes a one-element list. Serialization always emits an array.
     */
    private List<PaymentContextsPassenger> passenger;

    /**
     * Contains information about the flight leg(s) booked by the customer.
     * [Optional]
     */
    private List<PaymentContextsFlightLegDetails> flightLegDetails;
}
