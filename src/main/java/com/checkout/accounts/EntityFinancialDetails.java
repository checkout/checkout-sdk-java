package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntityFinancialDetails {

    @SerializedName("annual_processing_volume")
    private Long annualProcessingVolume;

    @SerializedName("average_transaction_value")
    private Long averageTransactionValue;

    @SerializedName("highest_transaction_value")
    private Long highestTransactionValue;

    private EntityFinancialDocuments documents;

}
