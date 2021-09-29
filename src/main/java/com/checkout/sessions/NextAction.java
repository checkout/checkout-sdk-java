package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum NextAction {

    @SerializedName("collect_channel_data")
    COLLECT_CHANNEL_DATA,
    @SerializedName("issuer_fingerprint")
    ISSUER_FINGERPRINT,
    @SerializedName("challenge_cardholder")
    CHALLENGE_CARDHOLDER,
    @SerializedName("redirect_cardholder")
    REDIRECT_CARDHOLDER,
    @SerializedName("complete")
    COMPLETE,
    @SerializedName("authenticate")
    AUTHENTICATE

}
