package com.checkout.balances;

import com.checkout.common.Currency;
import lombok.Data;

import java.time.Instant;

@Data
public final class CurrencyAccountBalance {

    /**
     * The unique identifier of the currency account (sub-account).
     * Returned only when the request is made with {@code withCurrencyAccountId=true}.
     * [Optional]
     */
    private String currencyAccountId;

    private String descriptor;

    private Currency holdingCurrency;

    private Balances balances;

    /**
     * The UTC datetime reflecting when the balance values were fetched. If the request includes a
     * {@code balancesAt} query parameter, this matches that value; otherwise, it is the time the
     * request was processed.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant balancesAsOf;

}
