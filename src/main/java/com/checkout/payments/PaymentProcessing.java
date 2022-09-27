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

    @SerializedName("partner_order_id")
    private String partnerOrderId;

    @SerializedName("partner_session_id")
    private String partnerSessionId;

    @SerializedName("partner_client_token")
    private String partnerClientToken;

    @SerializedName("partner_payment_id")
    private String partnerPaymentId;

    @SerializedName("continuation_payload")
    private String continuationPayload;

    private String pun;

}
