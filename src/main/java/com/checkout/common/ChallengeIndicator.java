package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum ChallengeIndicator {

    @SerializedName("no_preference")
    NO_PREFERENCE,
    @SerializedName("no_challenge_requested")
    NO_CHALLENGE_REQUESTED,
    @SerializedName("challenge_requested")
    CHALLENGE_REQUESTED,
    @SerializedName("challenge_requested_mandate")
    CHALLENGE_REQUESTED_MANDATE,
    @SerializedName("low_value")
    LOW_VALUE,
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,
    @SerializedName("trusted_listing_prompt")
    TRUSTED_LISTING_PROMPT,
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT,
    @SerializedName("data_share")
    DATA_SHARE

}
