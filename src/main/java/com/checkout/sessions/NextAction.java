package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum NextAction {

    @SerializedName("authenticate")
    AUTHENTICATE,
    @SerializedName("challenge_cardholder")
    CHALLENGE_CARDHOLDER,
    @SerializedName("collect_channel_data")
    COLLECT_CHANNEL_DATA,
    @SerializedName("complete")
    COMPLETE,
    @SerializedName("issuer_fingerprint")
    ISSUER_FINGERPRINT,
    @SerializedName("redirect_cardholder")
    REDIRECT_CARDHOLDER

}
