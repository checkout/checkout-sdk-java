package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Information about the payment aggregator.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Aggregator {

    /**
     * The sub-merchant ID.
     */
    private String subMerchantId;

    /**
     * The Visa identifier for the payment aggregator.
     */
    private String aggregatorIdVisa;

    /**
     * The Mastercard identifier for the payment aggregator.
     */
    private String aggregatorIdMc;
}
