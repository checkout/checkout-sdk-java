package com.checkout.identities.identityverification.responses;

import com.checkout.identities.entities.BaseAttemptResponse;
import com.checkout.identities.entities.IdentityVerificationClientInformation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Response for identity verification attempt operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IdentityVerificationAttemptResponse extends BaseAttemptResponse<IdentityVerificationAttemptStatus> {

    /**
     * The applicant's details.
     * [Optional]
     */
    private IdentityVerificationClientInformation clientInformation;
}
