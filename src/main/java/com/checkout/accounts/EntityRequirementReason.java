package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

public enum EntityRequirementReason {

    @SerializedName("periodic_review")
    PERIODIC_REVIEW,

    @SerializedName("attestation")
    ATTESTATION
}
