package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum ChallengeIndicator {

    @SerializedName("challenge_requested")
    CHALLENGE_REQUESTED,
    @SerializedName("challenge_requested_mandate")
    CHALLENGE_REQUESTED_MANDATE,
    @SerializedName("no_challenge_requested")
    NO_CHALLENGE_REQUESTED,
    @SerializedName("no_preference")
    NO_PREFERENCE,

}
