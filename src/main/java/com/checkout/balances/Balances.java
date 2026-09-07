package com.checkout.balances;

import lombok.Data;

/**
 * The balance values held by a currency account (sub-account).
 */
@Data
public final class Balances {

    /**
     * The total incoming funds that will be added to the Available balance once cleared.
     * [Optional]
     */
    private Long pending;

    /**
     * The funds that are available for processing.
     * [Optional]
     */
    private Long available;

    /**
     * The funds reserved from the Available balance for outgoing transactions that are yet to
     * clear.
     * [Optional]
     */
    private Long payable;

    /**
     * The funds held by Checkout.com to cover potential liabilities and risk events associated
     * with your account.
     * [Optional]
     */
    private Long collateral;

    /**
     * The funds held for processing Payouts and Issuing payments when the Available balance is
     * insufficient.
     * [Optional]
     */
    private Long operational;

    /**
     * A breakdown of the funds held in the {@code collateral} balance.
     * [Optional]
     */
    private CollateralBreakdown collateralBreakdown;

}
