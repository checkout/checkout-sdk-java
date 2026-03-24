package com.checkout.issuing.disputes.entities;

import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Amount details in dispute responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DisputeAmount {

    /**
     * The amount is provided in the minor currency unit
     */
    private Long amount;

    /**
     * The issuing currency, as a three-letter ISO currency code
     */
    private Currency currency;

}