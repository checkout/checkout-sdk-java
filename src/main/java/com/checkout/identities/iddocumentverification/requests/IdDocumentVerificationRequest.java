package com.checkout.identities.iddocumentverification.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ID document verification request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdDocumentVerificationRequest {

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

    /**
     * The personal details provided by the applicant.
     * [Required]
     */
    @SerializedName("declared_data")
    private DeclaredData declaredData;

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
        @SerializedName("name")
        private String name;
    }
}