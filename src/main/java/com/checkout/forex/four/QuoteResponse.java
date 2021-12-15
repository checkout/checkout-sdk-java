package com.checkout.forex.four;

import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Instant;

@Data
public final class QuoteResponse {

    private String id;

    @SerializedName("source_currency")
    private Currency sourceCurrency;

    @SerializedName("source_amount")
    private Long sourceAmount;

    @SerializedName("destination_currency")
    private Currency destinationCurrency;

    @SerializedName("destination_amount")
    private Long destinationAmount;

    private Double rate;

    @SerializedName("expires_on")
    private Instant expiresOn;

    @SerializedName("is_single_use")
    private boolean isSingleUse;

}
