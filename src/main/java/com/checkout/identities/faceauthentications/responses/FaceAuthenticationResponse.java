package com.checkout.identities.faceauthentications.responses;

import com.checkout.identities.entities.Face;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import com.checkout.identities.entities.BaseIdentityResponseStatus;

/**
 * Face authentication response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FaceAuthenticationResponse extends BaseIdentityResponseStatus<FaceAuthenticationStatus> {

    /**
     * The applicant's unique identifier.
     */
    private String applicantId;

    /**
     * Your configuration ID.
     */
    private String userJourneyId;

    /**
     * One or more codes that provide more information about risks associated with the verification.
     */
    private List<String> riskLabels;

    /**
     * The details of the image of the applicant's face extracted from the video.
     */
    private Face face;
}