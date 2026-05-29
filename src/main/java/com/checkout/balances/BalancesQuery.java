package com.checkout.balances;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class BalancesQuery {

    private String query;

    /**
     * When set to {@code true}, the response includes the {@code currency_account_id} on each
     * {@link CurrencyAccountBalance}.
     * [Optional]
     */
    @SerializedName("withCurrencyAccountId")
    private Boolean withCurrencyAccountId;

    /**
     * A UTC datetime to retrieve historical balances at a specific point in time.
     * Must be in the past. If omitted, the response returns live balances.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    @SerializedName("balancesAt")
    private Instant balancesAt;
}
