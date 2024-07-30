package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum SessionStatus {

    @SerializedName("approved")
    APPROVED,
    @SerializedName("attempted")
    ATTEMPTED,
    @SerializedName("challenge_abandoned")
    CHALLENGE_ABANDONED,
    @SerializedName("challenged")
    CHALLENGED,
    @SerializedName("declined")
    DECLINED,
    @SerializedName("expired")
    EXPIRED,
    @SerializedName("pending")
    PENDING,
    @SerializedName("processing")
    PROCESSING,
    @SerializedName("rejected")
    REJECTED,
    @SerializedName("unavailable")
    UNAVAILABLE,

}
