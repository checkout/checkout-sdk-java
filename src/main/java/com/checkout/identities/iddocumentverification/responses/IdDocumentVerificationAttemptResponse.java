package com.checkout.identities.iddocumentverification.responses;

import com.checkout.identities.entities.BaseIdentityResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for ID document verification attempt operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class IdDocumentVerificationAttemptResponse extends BaseIdentityResponseStatus<IdDocumentVerificationAttemptStatus> {
    // All fields are inherited from BaseAttemptResponse and BaseIdentityResponse
}