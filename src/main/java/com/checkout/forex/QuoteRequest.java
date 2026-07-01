package com.checkout.forex;

import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public final class QuoteRequest {

    @NonNull
    private Currency sourceCurrency;

    private Long sourceAmount;

    @NonNull
    private Currency destinationCurrency;

    private Long destinationAmount;

    @NonNull
    @SerializedName("processing_channel_id")
    private String processChannelId;

}
