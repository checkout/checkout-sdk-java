package com.checkout.payments;

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

    private Ticket ticket;

    private List<Passenger> passenger;

    @SerializedName("flight_leg_details")
    private List<FlightLegDetails> flightLegDetails;
}
