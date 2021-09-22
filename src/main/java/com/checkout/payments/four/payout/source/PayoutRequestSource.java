package com.checkout.payments.four.payout.source;

import lombok.Data;

@Data
public abstract class PayoutRequestSource {

    protected PayoutSourceType type;

    protected Long amount;

    protected PayoutRequestSource(final PayoutSourceType type) {
        this.type = type;
    }

}
