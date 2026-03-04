package com.checkout.identities.identityverification.requests;

import com.checkout.identities.entities.DeclaredData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identity verification creation request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityVerificationRequest {

    /**
     * The applicant's unique identifier.
     * [Required]
     */
    private String applicantId;

    /**
     * The personal details provided by the applicant.
     * [Required]
     */
    private DeclaredData declaredData;

    /**
     * Your configuration ID.
     */
    private String userJourneyId;
}