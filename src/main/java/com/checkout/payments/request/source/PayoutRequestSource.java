package com.checkout.payments.request.source;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class PayoutRequestSource {

    protected PayoutSourceType type;

    protected Long amount;

    protected PayoutRequestSource(final PayoutSourceType type) {
        this.type = type;
    }

}
