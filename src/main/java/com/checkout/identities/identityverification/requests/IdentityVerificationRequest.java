package com.checkout.identities.identityverification.requests;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("applicant_id")
    private String applicantId;

    /**
     * The personal details provided by the applicant.
     * [Required]
     */
    @SerializedName("declared_data")
    private DeclaredData declaredData;

    /**
     * Your configuration ID.
     */
    @SerializedName("user_journey_id")
    private String userJourneyId;

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