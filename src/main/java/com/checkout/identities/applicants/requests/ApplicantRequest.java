package com.checkout.identities.applicants.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Applicant creation request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ApplicantRequest {

    /**
     * Your reference for the applicant.
     */
    private String externalApplicantId;

    /**
     * The applicant's email address.
     */
    private String email;

    /**
     * The applicant's full name.
     */
    private String externalApplicantName;
}