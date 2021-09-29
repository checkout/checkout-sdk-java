package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum ChallengeCancelReason {

    @SerializedName("cardholder_cancel")
    CARDHOLDER_CANCEL,
    @SerializedName("transaction_timed_out")
    TRANSACTION_TIMED_OUT,
    @SerializedName("challenge_timed_out")
    CHALLENGE_TIMED_OUT,
    @SerializedName("transaction_error")
    TRANSACTION_ERROR,
    @SerializedName("sdk_timed_out")
    SDK_TIMED_OUT,
    @SerializedName("unknown")
    UNKNOWN

}
