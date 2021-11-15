package com.checkout.payments.four.sender;

import lombok.Data;

@Data
public abstract class PaymentSender {

    protected final RequestSenderType type;

    protected String reference;

    protected PaymentSender(final RequestSenderType type) {
        this.type = type;
    }

}
