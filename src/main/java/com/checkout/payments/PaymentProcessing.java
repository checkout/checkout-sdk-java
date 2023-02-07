package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

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

    @SerializedName("partner_status")
    private String partnerStatus;

    @SerializedName("partner_transaction_id")
    private String partnerTransactionId;

    @SerializedName("partner_error_codes")
    private List<String> partnerErrorCodes;

    @SerializedName("partner_error_message")
    private String partnerErrorMessage;

    @SerializedName("partner_authorization_code")
    private String partnerAuthorizationCode;

    @SerializedName("partner_authorization_response_code")
    private String partnerAuthorizationResponseCode;

}
