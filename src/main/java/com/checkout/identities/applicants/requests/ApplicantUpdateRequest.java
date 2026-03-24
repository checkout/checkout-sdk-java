package com.checkout.identities.applicants.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Applicant update request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ApplicantUpdateRequest {

    /**
     * The applicant's email address.
     */
    private String email;

    /**
     * The applicant's full name.
     */
    private String externalApplicantName;
}