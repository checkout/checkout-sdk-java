package com.checkout.identities.faceauthentications.responses;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Base class for attempt responses
 *
 * @param <T> The status enum type
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseAttemptResponse<T extends Enum<T>> extends BaseIdentityResponse<T> {

    /**
     * The URL to redirect the applicant to after the attempt.
     */
    @SerializedName("redirect_url")
    private String redirectUrl;

    /**
     * The applicant's details.
     */
    @SerializedName("client_information")
    private ClientInformation clientInformation;

    /**
     * The details of the attempt.
     */
    @SerializedName("applicant_session_information")
    private ApplicantSessionInformation applicantSessionInformation;

    /**
     * Client information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientInformation {
        /**
         * The applicant's residence country.
         */
        @SerializedName("pre_selected_residence_country")
        private String preSelectedResidenceCountry;

        /**
         * The language you want to use for the user interface.
         */
        @SerializedName("pre_selected_language")
        private String preSelectedLanguage;
    }

    /**
     * Session information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicantSessionInformation {
        /**
         * The applicant's IP address during the attempt.
         */
        @SerializedName("ip_address")
        private String ipAddress;
    }

}