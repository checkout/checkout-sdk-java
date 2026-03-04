package com.checkout.identities.iddocumentverification.requests;

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
    private String applicantId;

    /**
     * Your configuration ID.
     * [Required]
     */
    private String userJourneyId;

    /**
     * The personal details provided by the applicant.
     * [Required]
     */
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
        private String name;
    }
}