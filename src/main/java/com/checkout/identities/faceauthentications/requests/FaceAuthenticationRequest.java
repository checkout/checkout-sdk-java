package com.checkout.identities.faceauthentications.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Face authentication creation request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaceAuthenticationRequest {

    /**
     * The applicant's unique identifier.
     * [Required]
     */
    private String applicantId;

    /**
     * Your configuration ID.
     * [Required]
     */
    private String userJourneyId;
}