package com.checkout.identities.identityverification.responses;

import com.checkout.common.Resource;
import com.checkout.identities.entities.BaseIdentityResponseStatus;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * Response for identity verification operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityVerificationResponse extends BaseIdentityResponseStatus<IdentityVerificationStatus> {

    @SerializedName("id")
    private String id;

    @SerializedName("created_on")
    private Instant createdOn;

    @SerializedName("last_attempt_id")
    private String lastAttemptId;

    @SerializedName("applicant")
    private IdentityVerificationApplicant applicant;

    @SerializedName("check_status")
    private CheckStatus checkStatus;

    @SerializedName("report")
    private Report report;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class IdentityVerificationApplicant extends Resource {
        
        @SerializedName("id")
        private String id;
        
        @SerializedName("date_of_birth")
        private String dateOfBirth;
        
        @SerializedName("first_name")
        private String firstName;
        
        @SerializedName("last_name")
        private String lastName;
        
        @SerializedName("phone_number")
        private String phoneNumber;
        
        @SerializedName("email")
        private String email;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class CheckStatus extends Resource {
        
        @SerializedName("document_check")
        private String documentCheck;
        
        @SerializedName("face_authentication")
        private String faceAuthentication;
        
        @SerializedName("liveness_check")
        private String livenessCheck;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class Report extends Resource {
        
        @SerializedName("is_downloadable")
        private Boolean isDownloadable;
    }
}