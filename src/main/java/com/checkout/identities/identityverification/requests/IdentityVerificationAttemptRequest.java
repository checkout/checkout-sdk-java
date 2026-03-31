package com.checkout.identities.identityverification.requests;

import com.checkout.identities.entities.ClientInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identity verification attempt request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IdentityVerificationAttemptRequest {

    /**
     * The URL to redirect the applicant to after the attempt.
     * [Required]
     */
    private String redirectUrl;

    /**
     * The applicant's details.
     */
    private ClientInformation clientInformation;
}