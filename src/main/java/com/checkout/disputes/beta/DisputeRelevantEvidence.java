package com.checkout.disputes.beta;

import com.google.gson.annotations.SerializedName;

public enum DisputeRelevantEvidence {

    @SerializedName("proof_of_delivery_or_service")
    PROOF_OF_DELIVERY_OR_SERVICE,

    @SerializedName("invoice_or_receipt")
    INVOICE_OR_RECEIPT,

    @SerializedName("invoice_showing_distinct_transactions")
    INVOICE_SHOWING_DISTINCT_TRANSACTIONS,

    @SerializedName("customer_communication")
    CUSTOMER_COMMUNICATION,

    @SerializedName("refund_or_cancellation_policy")
    REFUND_OR_CANCELLATION_POLICY,

    @SerializedName("recurring_transaction_agreement")
    RECURRING_TRANSACTION_AGREEMENT,

    @SerializedName("additional_evidence")
    ADDITIONAL_EVIDENCE

}
