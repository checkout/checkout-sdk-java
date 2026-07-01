package com.checkout.payments.contexts;

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

    private List<PaymentContextsFlightLegDetails> flightLegDetails;
}
