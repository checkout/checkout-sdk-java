package com.checkout.disputes.four;

import com.google.gson.annotations.SerializedName;

public enum DisputeStatus {

    @SerializedName("won")
    WON,

    @SerializedName("lost")
    LOST,

    @SerializedName("expired")
    EXPIRED,

    @SerializedName("accepted")
    ACCEPTED,

    @SerializedName("canceled")
    CANCELED,

    @SerializedName("resolved")
    RESOLVED,

    @SerializedName("arbitration_won")
    ARBITRATION_WON,

    @SerializedName("arbitration_lost")
    ARBITRATION_LOST,

    @SerializedName("evidence_required")
    EVIDENCE_REQUIRED,

    @SerializedName("evidence_under_review")
    EVIDENCE_UNDER_REVIEW,

    @SerializedName("arbitration_under_review")
    ARBITRATION_UNDER_REVIEW

}
