package com.checkout.identities.addressdocumentverification.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for address document verification report operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class AddressDocumentVerificationReportResponse extends Resource {

    private String signedUrl;
}
