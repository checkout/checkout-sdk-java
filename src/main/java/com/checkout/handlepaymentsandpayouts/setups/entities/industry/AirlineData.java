package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import com.checkout.payments.contexts.PaymentContextsFlightLegDetails;
import com.checkout.payments.contexts.PaymentContextsPassenger;
import com.checkout.payments.contexts.PaymentContextsTicket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Airline industry-specific data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AirlineData {

    /**
     * The airline ticket information
     */
    private PaymentContextsTicket ticket;

    /**
     * List of passengers on the flight
     */
    private List<PaymentContextsPassenger> passengers;

    /**
     * Details of each leg of the flight journey
     */
    private List<PaymentContextsFlightLegDetails> flightLegDetails;

    /**
     * The total number of passengers on the booking.
     * [Optional]
     */
    private Long totalNumberOfPassengers;

    /**
     * The type of travel, for example "international" or "domestic".
     * [Optional]
     */
    private String travelType;

    /**
     * The type of trip, for example "one_way" or "round_trip".
     * [Optional]
     */
    private String tripType;

    /**
     * Specifies whether the booking is refundable.
     * [Optional]
     */
    private Boolean refundable;

    /**
     * The recipient the ticket is delivered to.
     * [Optional]
     */
    private String deliveryRecipient;

    /**
     * Any additional add-ons purchased with the booking, for example "extra_baggage".
     * [Optional]
     */
    private String ancillaries;

    /**
     * Details about the travel insurance purchased with the booking.
     * [Optional]
     */
    private AirlineInsurance insurance;
}