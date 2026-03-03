package com.checkout.identities.applicants.requests;

import com.google.gson.annotations.SerializedName;
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
public class ApplicantUpdateRequest {

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