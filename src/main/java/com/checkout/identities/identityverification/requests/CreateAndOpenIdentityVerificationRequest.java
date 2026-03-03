package com.checkout.identities.identityverification.requests;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("declared_data")
    private DeclaredData declaredData;

    /**
     * The URL to redirect the applicant to after the attempt.
     * [Required]
     */
    @SerializedName("redirect_url")
    private String redirectUrl;

    /**
     * Your configuration ID.
     */
    @SerializedName("user_journey_id")
    private String userJourneyId;

    /**
     * The applicant's unique identifier.
     */
    @SerializedName("applicant_id")
    private String applicantId;

    /**
     * Declared data nested class
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeclaredData {
        /**
         * The applicant's name.
         * [Required]
         */
        private String name;
    }
}