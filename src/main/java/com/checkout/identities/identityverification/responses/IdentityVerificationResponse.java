package com.checkout.identities.identityverification.responses;

import com.checkout.identities.entities.Face;
import com.checkout.identities.entities.VerifiedIdentity;
import com.checkout.identities.entities.DocumentDetails;
import com.checkout.identities.entities.BaseIdentityResponseStatus;
import com.checkout.identities.entities.DeclaredData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response for identity verification operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IdentityVerificationResponse extends BaseIdentityResponseStatus<IdentityVerificationStatus> {

    /**
     * The applicant's unique identifier.
     */
    private String applicantId;

    /**
     * Your configuration ID.
     */
    private String userJourneyId;

    /**
     * The personal details provided by the applicant.
     */
    private DeclaredData declaredData;

    /**
     * The URL to redirect the applicant to after the attempt.
     * (Only present in create-and-open response)
     */
    private String redirectUrl;

    /**
     * One or more codes that provide more information about risks associated with the verification.
     */
    private List<String> riskLabels;

    /**
     * The details of the applicant's identity documents.
     */
    private List<DocumentDetails> documents;

    /**
     * The details of the image of the applicant's face extracted from the video.
     */
    private Face face;

    /**
     * The details of the applicant's verified identity.
     */
    private VerifiedIdentity verifiedIdentity;
}