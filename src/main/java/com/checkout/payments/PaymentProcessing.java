package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class PaymentProcessing {

    @SerializedName("retrieval_reference_number")
    private String retrievalReferenceNumber;

    @SerializedName("acquirer_transaction_id")
    private String acquirerTransactionId;

    @SerializedName("recommendation_code")
    private String recommendationCode;

    @SerializedName("partner_payment_id")
    private String partnerPaymentId;

}
