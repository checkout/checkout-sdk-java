package com.checkout.common;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public enum FilePurpose {

    @SerializedName("dispute_evidence")
    DISPUTE_EVIDENCE("dispute_evidence"),

    @SerializedName("arbitration_evidence")
    ARBITRATION_EVIDENCE("arbitration_evidence"),

    @SerializedName("submitted_evidence")
    SUBMITTED_EVIDENCE("submitted_evidence");

    @Getter
    private final String purpose;

    FilePurpose(final String purpose) {
        this.purpose = purpose;
    }

}
