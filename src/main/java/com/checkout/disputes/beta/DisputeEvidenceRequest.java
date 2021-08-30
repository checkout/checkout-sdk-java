package com.checkout.disputes.beta;

import com.checkout.common.Link;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Map;

@Data
@Builder
public final class DisputeEvidenceRequest {

    @SerializedName("proof_of_delivery_or_service_file")
    private String proofOfDeliveryOrServiceFile;

    @Size(max = 500)
    @SerializedName("proof_of_delivery_or_service_text")
    private String proofOfDeliveryOrServiceText;

    @SerializedName("invoice_or_receipt_file")
    private String invoiceOrReceiptFile;

    @SerializedName("invoice_or_receipt_text")
    private String invoiceOrReceiptText;

    @SerializedName("invoice_showing_distinct_transactions_file")
    private String invoiceShowingDistinctTransactionsFile;

    @Size(max = 500)
    @SerializedName("invoice_showing_distinct_transactions_text")
    private String invoiceShowingDistinctTransactionsText;

    @SerializedName("customer_communication_file")
    private String customerCommunicationFile;

    @Size(max = 500)
    @SerializedName("customer_communication_text")
    private String customerCommunicationText;

    @SerializedName("refund_or_cancellation_policy_file")
    private String refundOrCancellationPolicyFile;

    @Size(max = 500)
    @SerializedName("refund_or_cancellation_policy_text")
    private String refundOrCancellationPolicyText;

    @SerializedName("recurring_transaction_agreement_file")
    private String recurringTransactionAgreementFile;

    @Size(max = 500)
    @SerializedName("recurring_transaction_agreement_text")
    private String recurringTransactionAgreementText;

    @SerializedName("additional_evidence_file")
    private String additionalEvidenceFile;

    @Size(max = 500)
    @SerializedName("additional_evidence_text")
    private String additionalEvidenceText;

    @SerializedName("proof_of_delivery_or_service_date_file")
    private String proofOfDeliveryOrServiceDateFile;

    @Size(max = 500)
    @SerializedName("proof_of_delivery_or_service_date_text")
    private String proofOfDeliveryOrServiceDateText;

    @SerializedName("_links")
    private Map<String, Link> links;

}
