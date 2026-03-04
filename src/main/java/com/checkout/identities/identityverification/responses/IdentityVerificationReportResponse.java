package com.checkout.identities.identityverification.responses;

import com.checkout.common.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Response for identity verification report download operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityVerificationReportResponse extends Resource {

    /**
     * The pre-signed URL to the captured image of the document.
     */
    private String signedUrl;

}