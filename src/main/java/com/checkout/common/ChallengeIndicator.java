package com.checkout.common;

import com.google.gson.annotations.SerializedName;

/**
 * Indicates whether a challenge is requested for an authentication.
 * <p>
 * The first four values are valid for all endpoints that accept a challenge indicator
 * (e.g. {@code POST /payments} {@code three_ds.challenge_indicator}, {@code POST /sessions},
 * and their responses).
 * <p>
 * The remaining values ({@link #LOW_VALUE}, {@link #TRUSTED_LISTING},
 * {@link #TRUSTED_LISTING_PROMPT}, {@link #TRANSACTION_RISK_ASSESSMENT}, {@link #DATA_SHARE})
 * represent requests for exemption and are only valid for {@code POST /sessions}
 * (3DS Standalone Authentication). If an exemption cannot be applied, the value
 * {@link #NO_CHALLENGE_REQUESTED} will be used instead.
 */
public enum ChallengeIndicator {

    @SerializedName("challenge_requested")
    CHALLENGE_REQUESTED,
    @SerializedName("challenge_requested_mandate")
    CHALLENGE_REQUESTED_MANDATE,
    @SerializedName("no_challenge_requested")
    NO_CHALLENGE_REQUESTED,
    @SerializedName("no_preference")
    NO_PREFERENCE,
    /**
     * Request a low-value exemption. Only valid for {@code POST /sessions}.
     */
    @SerializedName("low_value")
    LOW_VALUE,
    /**
     * Request a trusted listing exemption. Only valid for {@code POST /sessions}.
     */
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,
    /**
     * Request a trusted listing prompt to add the merchant to the cardholder's trusted list.
     * Only valid for {@code POST /sessions}.
     */
    @SerializedName("trusted_listing_prompt")
    TRUSTED_LISTING_PROMPT,
    /**
     * Request a transaction risk analysis (TRA) exemption. Only valid for {@code POST /sessions}.
     */
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT,
    /**
     * Indicates a data-share authentication request. Only valid for {@code POST /sessions}.
     */
    @SerializedName("data_share")
    DATA_SHARE,

}
