package com.checkout.payments.four.payout.source;

import lombok.Data;

@Data
public abstract class PayoutRequestSource {

    protected final PayoutSourceType type;

    protected final Long amount;

    protected PayoutRequestSource(final PayoutSourceType type,
                                  final Long amount) {
        this.type = type;
        this.amount = amount;
    }

}