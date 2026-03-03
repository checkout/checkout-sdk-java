package com.checkout.identities.faceauthentications.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Face authentication attempt creation request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaceAuthenticationAttemptRequest {

    /**
     * The URL to redirect the applicant to after the attempt.
     * [Required]
     */
    @SerializedName("redirect_url")
    private String redirectUrl;

    /**
     * The applicant's details.
     * [Optional]
     */
    @SerializedName("client_information")
    private ClientInformation clientInformation;

    /**
     * Client information for the face authentication attempt
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientInformation {
        /**
         * The applicant's residence country. Standard – ISO alpha-2 country code
         * Example – FR
         */
        @SerializedName("pre_selected_residence_country")
        private String preSelectedResidenceCountry;

        /**
         * The language you want to use for the user interface. 
         * Format – IETF BCP 47 language tag
         */
        @SerializedName("pre_selected_language")
        private String preSelectedLanguage;
    }
}