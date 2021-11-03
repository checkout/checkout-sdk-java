package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Processing {

    @SerializedName("retrieval_reference_number")
    private final String retrievalReferenceNumber;

    @SerializedName("acquirer_reference_number")
    private final String acquirerReferenceNumber;

    @SerializedName("acquirer_transaction_id")
    private final String acquirerTransactionId;

}