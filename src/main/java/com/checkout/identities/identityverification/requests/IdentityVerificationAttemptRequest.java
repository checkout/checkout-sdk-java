package com.checkout.identities.identityverification.requests;

import com.checkout.identities.entities.IdentityVerificationClientInformation;
import com.checkout.identities.entities.PhoneNumber;
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
     * Format: uri
     */
    private String redirectUrl;

    /**
     * The applicant's mobile phone number, if sharing the attempt URL via SMS.
     * [Optional]
     */
    private PhoneNumber phoneNumber;

    /**
     * The applicant's details.
     * [Optional]
     */
    private IdentityVerificationClientInformation clientInformation;
}
