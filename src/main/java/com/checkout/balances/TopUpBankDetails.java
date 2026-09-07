package com.checkout.balances;

import lombok.Data;

/**
 * The bank details for each available funding rail.
 * Both {@code domestic} and {@code international} are optional, and their availability depends on
 * the sub-account's holding currency, jurisdiction, and banking partner. Do not assume that both
 * rails are always available.
 */
@Data
public final class TopUpBankDetails {

    /**
     * The bank details for the domestic funding rail.
     * [Optional]
     */
    private TopUpFundingDetails domestic;

    /**
     * The bank details for the international funding rail.
     * [Optional]
     */
    private TopUpFundingDetails international;

}
