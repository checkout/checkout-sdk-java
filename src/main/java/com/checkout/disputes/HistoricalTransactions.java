package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class HistoricalTransactions {

    @SerializedName("historical_arn")
    private String historicalArn;

    @SerializedName("merchandise_or_service_desc")
    private String merchandiseOrServiceDesc;

}
