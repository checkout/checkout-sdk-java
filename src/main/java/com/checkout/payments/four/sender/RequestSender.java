package com.checkout.payments.four.sender;

import lombok.Data;

@Data
public abstract class RequestSender {

    protected final RequestSenderType type;

    protected String reference;

    protected RequestSender(final RequestSenderType type) {
        this.type = type;
    }

}
