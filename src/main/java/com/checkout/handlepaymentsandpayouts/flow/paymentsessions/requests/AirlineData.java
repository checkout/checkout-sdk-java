package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AirlineData {

    /**
     * Contains information about the airline ticket.
     */
    private Ticket ticket;

    /**
     * Contains information about the passenger(s) on the flight.
     */
    private List<Passenger> passenger;

    /**
     * Contains information about the flight leg(s) booked by the customer.
     */
    @SerializedName("flight_leg_details")
    private List<FlightLegDetails> flightLegDetails;

}
