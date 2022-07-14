package com.checkout.payments.previous.request.destination;

import com.checkout.payments.PaymentDestinationType;
import lombok.Data;

@Data
public abstract class PaymentRequestDestination {

    private final PaymentDestinationType type;

    protected PaymentRequestDestination(final PaymentDestinationType type) {
        this.type = type;
    }

}
