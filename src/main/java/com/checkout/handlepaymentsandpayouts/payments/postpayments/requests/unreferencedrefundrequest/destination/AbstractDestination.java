package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination;

import lombok.Data;
/**
 * Abstract destination Class
 * The destination of the unreferenced refund.
 */
@Data
public abstract class AbstractDestination {

    protected final DestinationType type;

    public AbstractDestination(final DestinationType type) {
        this.type = type;
    }

}
