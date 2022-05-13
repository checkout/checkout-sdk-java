package com.checkout.forex.four;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public final class QuoteResponse extends HttpMetadata {

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
