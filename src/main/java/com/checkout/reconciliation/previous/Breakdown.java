package com.checkout.reconciliation.previous;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class Breakdown {

    private String type;

    private String date;

    @SerializedName("processing_currency_amount")
    private String processingCurrencyAmount;

    @SerializedName("payout_currency_amount")
    private String payoutCurrencyAmount;

}
