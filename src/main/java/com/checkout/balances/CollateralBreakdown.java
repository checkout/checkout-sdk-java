package com.checkout.balances;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * A breakdown of the funds held in the {@code collateral} balance.
 */
@Data
public final class CollateralBreakdown {

    /**
     * The portion of the {@code collateral} balance held as a fixed reserve.
     * [Required]
     */
    @SerializedName("fixed_reserve")
    private Long fixedReserve;

    /**
     * The portion of the {@code collateral} balance held as a rolling reserve.
     * [Required]
     */
    @SerializedName("rolling_reserve")
    private Long rollingReserve;
}
