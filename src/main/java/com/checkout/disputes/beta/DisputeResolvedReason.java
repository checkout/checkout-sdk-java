package com.checkout.disputes.beta;

import com.google.gson.annotations.SerializedName;

public enum DisputeResolvedReason {

    @SerializedName("rapid_dispute_resolution")
    RAPID_DISPUTE_RESOLUTION,

    @SerializedName("negative_amount")
    NEGATIVE_AMOUNT,

    @SerializedName("already_refunded")
    ALREADY_REFUNDED

}
