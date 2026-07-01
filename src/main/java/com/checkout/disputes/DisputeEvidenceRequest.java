package com.checkout.disputes;

import com.checkout.common.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public final class DisputeEvidenceRequest extends Resource {

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

    private String arbitrationNoReviewText;

    private List<String> arbitrationNoReviewFiles;

    private String arbitrationReviewRequiredText;

    private List<String> arbitrationReviewRequiredFiles;

    private CompellingEvidence compellingEvidence;

}
