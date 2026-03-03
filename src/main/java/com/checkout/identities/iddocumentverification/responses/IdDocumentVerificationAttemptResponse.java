package com.checkout.identities.iddocumentverification.responses;

import com.checkout.identities.faceauthentications.responses.BaseAttemptResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for ID document verification attempt operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdDocumentVerificationAttemptResponse extends BaseAttemptResponse<IdDocumentVerificationAttemptStatus> {
    // All fields are inherited from BaseAttemptResponse and BaseIdentityResponse
}