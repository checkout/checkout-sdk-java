package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

/**
 * Indicates whether a challenge is requested for this session.
 * <p>
 * Used by {@link SessionRequest#getChallengeIndicator()} for {@code POST /sessions}
 * (3DS Standalone Authentication). This is the only field in the API that accepts the
 * exemption values below; the {@code 3ds.challenge_indicator} field on payments, hosted
 * payments, payment links and payment sessions accepts only the first four values and is
 * modelled by {@link com.checkout.common.ChallengeIndicator}.
 * <p>
 * The following are requests for exemption:
 * {@link #LOW_VALUE}, {@link #TRUSTED_LISTING}, {@link #TRUSTED_LISTING_PROMPT} and
 * {@link #TRANSACTION_RISK_ASSESSMENT}. If an exemption cannot be applied, then the value
 * {@link #NO_CHALLENGE_REQUESTED} will be used instead.
 * <p>
 * [Optional]
 * <p>
 * Default: {@link #NO_PREFERENCE}
 * <p>
 * max 50 characters
 */
public enum SessionChallengeIndicator {

    /**
     * No preference as to whether a challenge should be performed. This is the default.
     */
    @SerializedName("no_preference")
    NO_PREFERENCE,

    /**
     * A challenge is not requested for this session.
     */
    @SerializedName("no_challenge_requested")
    NO_CHALLENGE_REQUESTED,

    /**
     * A challenge is requested for this session.
     */
    @SerializedName("challenge_requested")
    CHALLENGE_REQUESTED,

    /**
     * A challenge is requested for this session because it is mandated by local regulation
     * or scheme rules.
     */
    @SerializedName("challenge_requested_mandate")
    CHALLENGE_REQUESTED_MANDATE,

    /**
     * Request a low-value exemption. If the exemption cannot be applied, the value
     * {@link #NO_CHALLENGE_REQUESTED} will be used instead.
     */
    @SerializedName("low_value")
    LOW_VALUE,

    /**
     * Request a trusted listing exemption, applied when the cardholder has already added the
     * merchant to their list of trusted beneficiaries. If the exemption cannot be applied, the
     * value {@link #NO_CHALLENGE_REQUESTED} will be used instead.
     */
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,

    /**
     * Request a trusted listing exemption and prompt the cardholder to add the merchant to their
     * list of trusted beneficiaries. If the exemption cannot be applied, the value
     * {@link #NO_CHALLENGE_REQUESTED} will be used instead.
     */
    @SerializedName("trusted_listing_prompt")
    TRUSTED_LISTING_PROMPT,

    /**
     * Request a transaction risk analysis (TRA) exemption. If the exemption cannot be applied,
     * the value {@link #NO_CHALLENGE_REQUESTED} will be used instead.
     */
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT,

    /**
     * Request a data-share authentication, where cardholder data is shared with the issuer to
     * support their risk assessment without requesting a challenge.
     */
    @SerializedName("data_share")
    DATA_SHARE,

}
