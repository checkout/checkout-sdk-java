package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public enum DisputeStatus {

    @SerializedName("won")
    WON("won"),

    @SerializedName("lost")
    LOST("lost"),

    @SerializedName("expired")
    EXPIRED("expired"),

    @SerializedName("accepted")
    ACCEPTED("accepted"),

    @SerializedName("canceled")
    CANCELED("canceled"),

    @SerializedName("resolved")
    RESOLVED("resolved"),

    @SerializedName("arbitration_won")
    ARBITRATION_WON("arbitration_won"),

    @SerializedName("arbitration_lost")
    ARBITRATION_LOST("arbitration_lost"),

    @SerializedName("evidence_required")
    EVIDENCE_REQUIRED("evidence_required"),

    @SerializedName("evidence_under_review")
    EVIDENCE_UNDER_REVIEW("evidence_under_review"),

    @SerializedName("arbitration_under_review")
    ARBITRATION_UNDER_REVIEW("arbitration_under_review");

    @Getter
    private final String status;

    DisputeStatus(final String status) {
        this.status = status;
    }

}
