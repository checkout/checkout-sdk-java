package com.checkout.disputes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DisputeEvidenceResponse {

    private String proofOfDeliveryOrServiceFile;
    private String proofOfDeliveryOrServiceText;
    private String invoiceOrReceiptFile;
    private String invoiceOrReceiptText;
    private String invoiceShowingDistinctTransactionsFile;
    private String invoiceShowingDistinctTransactionsText;
    private String customerCommunicationFile;
    private String customerCommunicationText;
    private String refundOrCancellationPolicyFile;
    private String refundOrCancellationPolicyText;
    private String recurringTransactionAgreementFile;
    private String recurringTransactionAgreementText;
    private String additionalEvidenceFile;
    private String additionalEvidenceText;
    private String proofOfDeliveryOrServiceDateFile;
    private String proofOfDeliveryOrServiceDateText;

}
