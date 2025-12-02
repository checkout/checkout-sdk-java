package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import com.checkout.payments.contexts.PaymentContextsFlightLegDetails;
import com.checkout.payments.contexts.PaymentContextsPassenger;
import com.checkout.payments.contexts.PaymentContextsTicket;
import com.google.gson.annotations.SerializedName;
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
public class AirlineData {

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
    @SerializedName("flight_leg_details")
    private List<PaymentContextsFlightLegDetails> flightLegDetails;
}