package com.checkout.identities.amlscreening.responses;

import com.google.gson.annotations.SerializedName;

/**
 * The status of an AML screening
 */
public enum AmlScreeningStatus {

    @SerializedName("created")
    CREATED,

    @SerializedName("screening_in_progress")
    SCREENING_IN_PROGRESS,

    @SerializedName("approved")
    APPROVED,

    @SerializedName("declined")
    DECLINED,

    @SerializedName("review_required")
    REVIEW_REQUIRED

}