package com.checkout.payments.request.destination;

import com.checkout.payments.PaymentDestinationType;
import lombok.Data;

@Data
public abstract class PaymentRequestDestination {

    private final PaymentDestinationType type;

    public PaymentRequestDestination(final PaymentDestinationType type) {
        this.type = type;
    }

}
