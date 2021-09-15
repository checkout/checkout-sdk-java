package com.checkout.payments.four.request.source;

import com.checkout.common.PaymentSourceType;
import lombok.Data;

@Data
public abstract class RequestSource {

    protected final PaymentSourceType type;

    protected RequestSource(final PaymentSourceType type) {
        this.type = type;
    }

}