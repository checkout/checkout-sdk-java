package com.checkout.identities.faceauthentications.responses;

import com.google.gson.annotations.SerializedName;

/**
 * The type of asset captured during a face authentication attempt.
 */
public enum FaceAuthenticationAttemptAssetType {

    @SerializedName("face_image")
    FACE_IMAGE,
    @SerializedName("face_video")
    FACE_VIDEO

}
