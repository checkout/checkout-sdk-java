package com.checkout.reconciliation;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Instant;

@Data
public final class Breakdown {

    private String type;

    private Instant date;

    @SerializedName("processing_currency_amount")
    private String processingCurrencyAmount;

    @SerializedName("payout_currency_amount")
    private String payoutCurrencyAmount;

}
