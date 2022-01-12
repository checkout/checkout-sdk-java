package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum SessionStatus {

    @SerializedName("pending")
    PENDING,
    @SerializedName("processing")
    PROCESSING,
    @SerializedName("challenged")
    CHALLENGED,
    @SerializedName("challenge_abandoned")
    CHALLENGE_ABANDONED,
    @SerializedName("expired")
    EXPIRED,
    @SerializedName("approved")
    APPROVED,
    @SerializedName("attempted")
    ATTEMPTED,
    @SerializedName("unavailable")
    UNAVAILABLE,
    @SerializedName("declined")
    DECLINED,
    @SerializedName("rejected")
    REJECTED

}
