package com.checkout.forex.four;

import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public final class QuoteRequest {

    @NonNull
    @SerializedName("source_currency")
    private Currency sourceCurrency;

    @SerializedName("source_amount")
    private Long sourceAmount;

    @NonNull
    @SerializedName("destination_currency")
    private Currency destinationCurrency;

    @SerializedName("destination_amount")
    private Long destinationAmount;

    @NonNull
    @SerializedName("processing_channel_id")
    private String processChannelId;

}
