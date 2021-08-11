package com.checkout.disputes;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
public final class DisputeEvidenceRequest {

    private String proofOfDeliveryOrServiceFile;
    @Size(max = 500)
    private String proofOfDeliveryOrServiceText;
    private String invoiceOrReceiptFile;
    private String invoiceOrReceiptText;
    private String invoiceShowingDistinctTransactionsFile;
    @Size(max = 500)
    private String invoiceShowingDistinctTransactionsText;
    private String customerCommunicationFile;
    @Size(max = 500)
    private String customerCommunicationText;
    private String refundOrCancellationPolicyFile;
    @Size(max = 500)
    private String refundOrCancellationPolicyText;
    private String recurringTransactionAgreementFile;
    @Size(max = 500)
    private String recurringTransactionAgreementText;
    private String additionalEvidenceFile;
    @Size(max = 500)
    private String additionalEvidenceText;
    private String proofOfDeliveryOrServiceDateFile;
    @Size(max = 500)
    private String proofOfDeliveryOrServiceDateText;

}
