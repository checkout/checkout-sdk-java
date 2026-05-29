package com.checkout.balances;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("collateral_breakdown")
    private CollateralBreakdown collateralBreakdown;

}
