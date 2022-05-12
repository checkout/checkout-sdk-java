package com.checkout.payments.four.sender;

import lombok.Data;

@Data
public abstract class PaymentSender implements Sender {

    protected final SenderType type;

    protected String reference;

    protected PaymentSender(final SenderType type) {
        this.type = type;
    }

}
