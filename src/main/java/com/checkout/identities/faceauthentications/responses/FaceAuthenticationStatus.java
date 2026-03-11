package com.checkout.identities.faceauthentications.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Face authentication status enumeration
 */
public enum FaceAuthenticationStatus {

    @SerializedName("approved")
    APPROVED,

    @SerializedName("capture_in_progress")
    CAPTURE_IN_PROGRESS,

    @SerializedName("checks_in_progress")
    CHECKS_IN_PROGRESS,

    @SerializedName("created")
    CREATED,

    @SerializedName("declined")
    DECLINED,

    @SerializedName("inconclusive")
    INCONCLUSIVE,

    @SerializedName("pending")
    PENDING,

    @SerializedName("refused")
    REFUSED,

    @SerializedName("retry_required")
    RETRY_REQUIRED
    
}