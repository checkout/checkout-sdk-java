package com.checkout.identities.identityverification.requests;

import com.checkout.identities.entities.DeclaredData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create and open identity verification request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAndOpenIdentityVerificationRequest {

    /**
     * The personal details provided by the applicant.
     * [Required]
     */
    private DeclaredData declaredData;

    /**
     * The URL to redirect the applicant to after the attempt.
     * [Required]
     */
    private String redirectUrl;

    /**
     * Your configuration ID.
     */
    private String userJourneyId;

    /**
     * The applicant's unique identifier.
     */
    private String applicantId;
}