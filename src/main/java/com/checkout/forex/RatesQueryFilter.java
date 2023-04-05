package com.checkout.forex;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class RatesQueryFilter {

    @NonNull
    private String product;

    @NonNull
    private ForexSource source;

    @NonNull
    @SerializedName("currency_pairs")
    private String currencyPairs;

    @NonNull
    @SerializedName("processing_channel_id")
    private String processChannelId;
}
