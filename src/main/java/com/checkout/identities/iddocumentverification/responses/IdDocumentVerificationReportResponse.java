package com.checkout.identities.iddocumentverification.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for ID document verification report operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class IdDocumentVerificationReportResponse extends Resource {

    /**
     * The pre-signed URL to the PDF report.
     * [Required]
     * Format: uri
     */
    private String pdfReport;
}