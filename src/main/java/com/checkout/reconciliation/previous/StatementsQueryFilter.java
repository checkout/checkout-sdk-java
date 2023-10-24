package com.checkout.reconciliation.previous;

import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class StatementsQueryFilter {

    @SerializedName("payout_id")
    private String payoutId;

    @SerializedName("payout_currency")
    private Currency payoutCurrency;

    private Instant from;

    private Instant to;

    private IncludePayouts include;
}
