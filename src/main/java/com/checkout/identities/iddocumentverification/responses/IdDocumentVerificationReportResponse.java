package com.checkout.identities.iddocumentverification.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for ID document verification report operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdDocumentVerificationReportResponse extends Resource {

    private String signedUrl;
}