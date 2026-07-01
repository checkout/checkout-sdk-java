package com.checkout.balances;

import lombok.Data;

@Data
public final class Balances {

    private Long pending;

    private Long available;

    private Long payable;

    private Long collateral;

    /**
     * A breakdown of the funds held in the {@code collateral} balance.
     * [Optional]
     */
    private CollateralBreakdown collateralBreakdown;

}
