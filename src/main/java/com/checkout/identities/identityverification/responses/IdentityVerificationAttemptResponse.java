package com.checkout.identities.identityverification.responses;

import com.checkout.common.Resource;
import com.checkout.identities.entities.BaseAttemptResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * Response for identity verification attempt operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityVerificationAttemptResponse extends BaseAttemptResponse<IdentityVerificationAttemptStatus> {

    @SerializedName("id")
    private String id;

    @SerializedName("created_on")
    private Instant createdOn;

    @SerializedName("last_updated")
    private Instant lastUpdated;

    @SerializedName("reference")
    private String reference;

    @SerializedName("verification_information")
    private VerificationInformation verificationInformation;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class VerificationInformation extends Resource {

        @SerializedName("device_fingerprint")
        private String deviceFingerprint;

        @SerializedName("ip_address")
        private String ipAddress;

        @SerializedName("user_agent")
        private String userAgent;
    }
}