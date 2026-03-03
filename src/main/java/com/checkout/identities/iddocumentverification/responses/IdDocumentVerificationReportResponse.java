package com.checkout.identities.iddocumentverification.responses;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for ID document verification report operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdDocumentVerificationReportResponse extends Resource {

    @SerializedName("signed_url")
    private String signedUrl;

    @SerializedName("pdf_report")
    private String pdfReport;
}