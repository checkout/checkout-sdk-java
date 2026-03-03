package com.checkout.identities.identityverification.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identity verification attempt request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityVerificationAttemptRequest {

    /**
     * The URL to redirect the applicant to after the attempt.
     * [Required]
     */
    @SerializedName("redirect_url")
    private String redirectUrl;

    /**
     * The applicant's details.
     */
    @SerializedName("verification_information")
    private VerificationInformation verificationInformation;

    /**
     * Client information nested class
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationInformation {
        /**
         * Device fingerprint information
         */
        @SerializedName("device_fingerprint")
        private String deviceFingerprint;

        /**
         * Client IP address
         */
        @SerializedName("ip_address")
        private String ipAddress;

        /**
         * User agent string
         */
        @SerializedName("user_agent")
        private String userAgent;
    }
}