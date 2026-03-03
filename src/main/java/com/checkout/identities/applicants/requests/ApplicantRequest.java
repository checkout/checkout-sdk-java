package com.checkout.identities.applicants.requests;

import com.google.gson.annotations.SerializedName;
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
public class ApplicantRequest {

    /**
     * Your reference for the applicant.
     */
    @SerializedName("external_applicant_id")
    private String externalApplicantId;

    /**
     * The applicant's email address.
     */
    private String email;

    /**
     * The applicant's full name.
     */
    @SerializedName("external_applicant_name")
    private String externalApplicantName;
}