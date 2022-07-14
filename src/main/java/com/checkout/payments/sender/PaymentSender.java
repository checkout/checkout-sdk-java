package com.checkout.payments.sender;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class PaymentSender implements Sender {

    protected final SenderType type;

    protected String reference;

    protected PaymentSender(final SenderType type) {
        this.type = type;
    }

}
