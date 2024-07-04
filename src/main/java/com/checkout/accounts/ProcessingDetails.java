package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Currency;
import java.util.List;

@Data
@Builder
public final class ProcessingDetails {

    @SerializedName("settlement_country")
    private String settlementCountry;

    @SerializedName("target_countries")
    private List<String> targetCountries;

    @SerializedName("annual_processing_volume")
    private Integer annualProcessingVolume;

    @SerializedName("average_transaction_value")
    private Integer averageTransactionValue;

    @SerializedName("highest_transaction_value")
    private Integer highestTransactionValue;

    private Currency currency;

}
