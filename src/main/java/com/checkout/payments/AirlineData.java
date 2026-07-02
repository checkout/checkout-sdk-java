package com.checkout.payments;

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

    private Ticket ticket;

    private List<Passenger> passenger;

    private List<FlightLegDetails> flightLegDetails;
}
