package com.checkout.disputes;

import com.checkout.HttpMetadata;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class DisputeEvidenceResponse extends HttpMetadata {

    @SerializedName("proof_of_delivery_or_service_file")
    private String proofOfDeliveryOrServiceFile;

    @SerializedName("proof_of_delivery_or_service_text")
    private String proofOfDeliveryOrServiceText;

    @SerializedName("invoice_or_receipt_file")
    private String invoiceOrReceiptFile;

    @SerializedName("invoice_or_receipt_text")
    private String invoiceOrReceiptText;

    @SerializedName("invoice_showing_distinct_transactions_file")
    private String invoiceShowingDistinctTransactionsFile;

    @SerializedName("invoice_showing_distinct_transactions_text")
    private String invoiceShowingDistinctTransactionsText;

    @SerializedName("customer_communication_file")
    private String customerCommunicationFile;

    @SerializedName("customer_communication_text")
    private String customerCommunicationText;

    @SerializedName("refund_or_cancellation_policy_file")
    private String refundOrCancellationPolicyFile;

    @SerializedName("refund_or_cancellation_policy_text")
    private String refundOrCancellationPolicyText;

    @SerializedName("recurring_transaction_agreement_file")
    private String recurringTransactionAgreementFile;

    @SerializedName("recurring_transaction_agreement_text")
    private String recurringTransactionAgreementText;

    @SerializedName("additional_evidence_file")
    private String additionalEvidenceFile;

    @SerializedName("additional_evidence_text")
    private String additionalEvidenceText;

    @SerializedName("proof_of_delivery_or_service_date_file")
    private String proofOfDeliveryOrServiceDateFile;

    @SerializedName("proof_of_delivery_or_service_date_text")
    private String proofOfDeliveryOrServiceDateText;

    @SerializedName("arbitration_no_review_text")
    private String arbitrationNoReviewText;

    @SerializedName("arbitration_no_review_files")
    private List<String> arbitrationNoReviewFiles;

    @SerializedName("arbitration_review_required_text")
    private String arbitrationReviewRequiredText;

    @SerializedName("arbitration_review_required_files")
    private List<String> arbitrationReviewRequiredFiles;

    @SerializedName("compelling_evidence")
    private CompellingEvidence compellingEvidence;

}
