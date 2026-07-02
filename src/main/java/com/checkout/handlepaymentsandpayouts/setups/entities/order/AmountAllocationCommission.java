package com.checkout.handlepaymentsandpayouts.setups.entities.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Commission you'd like to collect from this split - this will be credited to your currency account.
 * The commission cannot exceed the split amount.
 * Commission = (amount * commission.percentage) + commission.amount
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AmountAllocationCommission {

    /**
     * Optional fixed amount of commission to collect, in the minor currency unit.
     * [Optional]
     * min 0
     */
    private Long amount;

    /**
     * Optional percentage of commission to collect. Supports up to 8 decimal places.
     * [Optional]
     * min 0, max 100
     */
    private Double percentage;
}
