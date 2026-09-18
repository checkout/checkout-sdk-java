package com.checkout.identities.addressdocumentverification.responses;

import com.checkout.identities.entities.BaseIdentityResponseStatus;
import com.checkout.identities.entities.DeclaredData;
import com.checkout.identities.entities.RiskLabel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response for address document verification operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class AddressDocumentVerificationResponse extends BaseIdentityResponseStatus<AddressDocumentVerificationStatus> {

    /**
     * Your configuration ID.
     * [Required]
     */
    private String userJourneyId;

    /**
     * The applicant's unique identifier.
     * [Required]
     */
    private String applicantId;

    /**
     * The personal details provided by the applicant.
     * [Optional]
     */
    private DeclaredData declaredData;

    /**
     * One or more codes that provide more information about risks associated with the
     * verification.
     * [Optional]
     */
    private List<RiskLabel> riskLabels;

    /**
     * The result of the address document check.
     * [Optional]
     */
    private AddressDocumentResult addressDocument;
}
