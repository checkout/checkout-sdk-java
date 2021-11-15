package com.checkout.payments.four.request.destination;

import com.checkout.payments.PaymentDestinationType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentRequestIdDestination extends PaymentRequestDestination {

    private String id;

    @Builder
    private PaymentRequestIdDestination(final String id) {
        super(PaymentDestinationType.ID);
        this.id = id;
    }

    public PaymentRequestIdDestination() {
        super(PaymentDestinationType.ID);
    }

}
