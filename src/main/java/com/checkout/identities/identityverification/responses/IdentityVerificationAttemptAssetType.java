package com.checkout.identities.identityverification.responses;

import com.google.gson.annotations.SerializedName;

/**
 * The type of asset captured during an identity verification attempt.
 */
public enum IdentityVerificationAttemptAssetType {

    @SerializedName("face_image")
    FACE_IMAGE,
    @SerializedName("face_video")
    FACE_VIDEO,
    @SerializedName("document_front_image")
    DOCUMENT_FRONT_IMAGE,
    @SerializedName("document_back_image")
    DOCUMENT_BACK_IMAGE,
    @SerializedName("document_front_video")
    DOCUMENT_FRONT_VIDEO,
    @SerializedName("document_back_video")
    DOCUMENT_BACK_VIDEO,
    @SerializedName("document_signature_image")
    DOCUMENT_SIGNATURE_IMAGE,
    @SerializedName("secondary_document_front_image")
    SECONDARY_DOCUMENT_FRONT_IMAGE,
    @SerializedName("secondary_document_back_image")
    SECONDARY_DOCUMENT_BACK_IMAGE,
    @SerializedName("secondary_document_front_video")
    SECONDARY_DOCUMENT_FRONT_VIDEO,
    @SerializedName("secondary_document_back_video")
    SECONDARY_DOCUMENT_BACK_VIDEO,
    @SerializedName("secondary_document_signature_image")
    SECONDARY_DOCUMENT_SIGNATURE_IMAGE

}
