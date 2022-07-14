package com.checkout.payments.previous.request.source;

import com.checkout.common.PaymentSourceType;
import lombok.Data;

@Data
public abstract class AbstractRequestSource {

    protected final PaymentSourceType type;

    protected AbstractRequestSource(final PaymentSourceType type) {
        this.type = type;
    }

}