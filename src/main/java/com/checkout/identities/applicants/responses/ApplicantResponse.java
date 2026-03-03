package com.checkout.identities.applicants.responses;

import com.checkout.identities.faceauthentications.responses.BaseIdentityResponse;
import com.google.gson.annotations.SerializedName;
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
public class ApplicantResponse extends BaseIdentityResponse<ApplicantStatus> {

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