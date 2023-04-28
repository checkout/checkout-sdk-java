package com.checkout.issuing.cards.requests.revoke;

import com.google.gson.annotations.SerializedName;

public enum RevokeReason {

    @SerializedName("expired")
    EXPIRED,
    @SerializedName("reported_lost")
    REPORTED_LOST,
    @SerializedName("reported_stolen")
    REPORTED_STOLEN

}
