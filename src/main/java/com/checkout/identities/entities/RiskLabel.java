package com.checkout.identities.entities;

import com.google.gson.annotations.SerializedName;

/**
 * A code that provides more information about a risk associated with the verification.
 */
public enum RiskLabel {

    @SerializedName("multiple_faces_detected")
    MULTIPLE_FACES_DETECTED,
    @SerializedName("mcc_not_confident")
    MCC_NOT_CONFIDENT,
    @SerializedName("risky_document_format")
    RISKY_DOCUMENT_FORMAT

}
