package com.checkout.identities.addressdocumentverification.requests;

import com.checkout.identities.entities.DeclaredData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Address document verification request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AddressDocumentVerificationRequest {

    /**
     * The applicant's unique identifier.
     * [Required]
     * Pattern: ^aplt_\w+$
     */
    private String applicantId;

    /**
     * Your configuration ID.
     * [Required]
     * Pattern: ^usj_[a-z2-7]{26}$
     */
    private String userJourneyId;

    /**
     * The personal details provided by the applicant.
     * [Optional]
     */
    private DeclaredData declaredData;
}
