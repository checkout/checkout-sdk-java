package com.checkout.identities.faceauthentications.requests;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("applicant_id")
    private String applicantId;

    /**
     * Your configuration ID.
     * [Required]
     */
    @SerializedName("user_journey_id")
    private String userJourneyId;
}