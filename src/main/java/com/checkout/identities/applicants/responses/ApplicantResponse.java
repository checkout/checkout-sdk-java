package com.checkout.identities.applicants.responses;

import com.checkout.identities.entities.BaseIdentityResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Applicant response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ApplicantResponse extends BaseIdentityResponse{

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