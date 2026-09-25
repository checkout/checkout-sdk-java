package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A key-and-value pair with merchant-specific data for the transaction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PartnerCustomerRiskData {

    /**
     * The key for the pair.
     * [Optional]
     */
    private String key;

    /**
     * The value for the pair.
     * [Optional]
     */
    private String value;
}
