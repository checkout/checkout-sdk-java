package com.checkout.identities.faceauthentications.responses;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Base class for verification responses that contain applicant-related information
 *
 * @param <T> The status enum type
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseVerificationResponse<T extends Enum<T>> extends BaseIdentityResponse<T> {

    /**
     * The applicant's unique identifier.
     */
    @SerializedName("applicant_id")
    private String applicantId;

    /**
     * Your configuration ID.
     */
    @SerializedName("user_journey_id")
    private String userJourneyId;

    /**
     * One or more codes that provide more information about risks associated with the verification.
     */
    @SerializedName("risk_labels")
    private List<String> riskLabels;

}