package com.checkout.identities.faceauthentications.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Face authentication status enumeration
 */
public enum FaceAuthenticationStatus {

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

    @SerializedName("rejected")
    REJECTED

}