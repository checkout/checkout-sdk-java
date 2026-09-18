package com.checkout.identities.iddocumentverification.responses;

import com.google.gson.annotations.SerializedName;

/**
 * The type of asset uploaded for an ID document verification attempt.
 */
public enum IdDocumentVerificationAttemptAssetType {

    @SerializedName("document_front_image")
    DOCUMENT_FRONT_IMAGE,
    @SerializedName("document_back_image")
    DOCUMENT_BACK_IMAGE

}
