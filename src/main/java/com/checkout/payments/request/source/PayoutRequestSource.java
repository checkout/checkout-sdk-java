package com.checkout.payments.request.source;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class PayoutRequestSource {

    /**
     * The payout source type.
     * [Required]
     */
    protected PayoutSourceType type;

    /**
     * The amount to debit from the source. If not provided, the full payment amount is used.
     * [Optional]
     */
    protected Long amount;

    protected PayoutRequestSource(final PayoutSourceType type) {
        this.type = type;
    }

}
