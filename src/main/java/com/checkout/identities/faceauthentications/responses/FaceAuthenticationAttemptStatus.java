package com.checkout.identities.faceauthentications.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Face authentication attempt status enumeration
 */
public enum FaceAuthenticationAttemptStatus {

    @SerializedName("pending")
    PENDING,

    @SerializedName("processing")
    PROCESSING,

    @SerializedName("completed")
    COMPLETED,

    @SerializedName("failed")
    FAILED,

    @SerializedName("expired")
    EXPIRED,

    @SerializedName("abandoned")
    ABANDONED

}