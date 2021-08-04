package com.checkout.payments.beta.request.source;

import lombok.Data;

@Data
public abstract class RequestSource {

    protected final PaymentSourceType type;

    protected RequestSource(final PaymentSourceType type) {
        this.type = type;
    }

}