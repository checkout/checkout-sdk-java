package com.checkout.payments.four.payout.destination;

import lombok.Data;

@Data
public abstract class PaymentDestination {

    private final PaymentDestinationType type;

    public PaymentDestination(final PaymentDestinationType type) {
        this.type = type;
    }

}