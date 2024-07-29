package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum ChallengeIndicator {

    @SerializedName("challenge_requested")
    CHALLENGE_REQUESTED,
    @SerializedName("challenge_requested_mandate")
    CHALLENGE_REQUESTED_MANDATE,
    @SerializedName("data_share")
    DATA_SHARE,
    @SerializedName("low_value")
    LOW_VALUE,
    @SerializedName("no_challenge_requested")
    NO_CHALLENGE_REQUESTED,
    @SerializedName("no_preference")
    NO_PREFERENCE,
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT,
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,
    @SerializedName("trusted_listing_prompt")
    TRUSTED_LISTING_PROMPT

}
