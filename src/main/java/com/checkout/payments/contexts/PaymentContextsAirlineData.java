package com.checkout.payments.contexts;

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
public final class PaymentContextsAirlineData {

    private PaymentContextsTicket ticket;

    private List<PaymentContextsPassenger> passenger;

    @SerializedName("flight_leg_details")
    private List<PaymentContextsFlightLegDetails> flightLegDetails;
}
