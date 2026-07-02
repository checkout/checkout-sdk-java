package com.checkout.disputes;

import com.checkout.HttpMetadata;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class DisputeEvidenceResponse extends HttpMetadata {

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

    private String arbitrationNoReviewText;

    private List<String> arbitrationNoReviewFiles;

    private String arbitrationReviewRequiredText;

    private List<String> arbitrationReviewRequiredFiles;

    private CompellingEvidence compellingEvidence;

}
