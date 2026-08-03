package com.checkout.common;

import com.google.gson.annotations.SerializedName;

/**
 * Indicates the preference for whether or not a 3DS challenge should be performed. The customer's
 * bank has the final say on whether or not the customer receives the challenge.
 * <p>
 * This is the four-value indicator accepted by the {@code 3ds.challenge_indicator} field on
 * {@code POST /payments}, {@code POST /hosted-payments}, {@code POST /payment-links} and
 * {@code POST /payment-sessions}.
 * <p>
 * [Optional]
 * <p>
 * Default: {@link #NO_PREFERENCE}
 *
 * @see com.checkout.sessions.SessionChallengeIndicator the wider nine-value enum accepted by
 * {@code POST /sessions}, which additionally supports requests for exemption
 */
public enum ChallengeIndicator {

    /**
     * A challenge is requested for this payment.
     */
    @SerializedName("challenge_requested")
    CHALLENGE_REQUESTED,

    /**
     * A challenge is requested for this payment because it is mandated by local regulation or
     * scheme rules.
     */
    @SerializedName("challenge_requested_mandate")
    CHALLENGE_REQUESTED_MANDATE,

    /**
     * A challenge is not requested for this payment.
     */
    @SerializedName("no_challenge_requested")
    NO_CHALLENGE_REQUESTED,

    /**
     * No preference as to whether a challenge should be performed. This is the default.
     */
    @SerializedName("no_preference")
    NO_PREFERENCE,

    /**
     * Request a low-value exemption.
     *
     * @deprecated only valid for {@code POST /sessions}, which is now modelled by
     * {@link com.checkout.sessions.SessionChallengeIndicator#LOW_VALUE}. This value is rejected by
     * the {@code 3ds.challenge_indicator} fields that use this enum.
     */
    @Deprecated
    @SerializedName("low_value")
    LOW_VALUE,

    /**
     * Request a trusted listing exemption.
     *
     * @deprecated only valid for {@code POST /sessions}, which is now modelled by
     * {@link com.checkout.sessions.SessionChallengeIndicator#TRUSTED_LISTING}. This value is
     * rejected by the {@code 3ds.challenge_indicator} fields that use this enum.
     */
    @Deprecated
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,

    /**
     * Request a trusted listing prompt to add the merchant to the cardholder's trusted list.
     *
     * @deprecated only valid for {@code POST /sessions}, which is now modelled by
     * {@link com.checkout.sessions.SessionChallengeIndicator#TRUSTED_LISTING_PROMPT}. This value is
     * rejected by the {@code 3ds.challenge_indicator} fields that use this enum.
     */
    @Deprecated
    @SerializedName("trusted_listing_prompt")
    TRUSTED_LISTING_PROMPT,

    /**
     * Request a transaction risk analysis (TRA) exemption.
     *
     * @deprecated only valid for {@code POST /sessions}, which is now modelled by
     * {@link com.checkout.sessions.SessionChallengeIndicator#TRANSACTION_RISK_ASSESSMENT}. This
     * value is rejected by the {@code 3ds.challenge_indicator} fields that use this enum.
     */
    @Deprecated
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT,

    /**
     * Indicates a data-share authentication request.
     *
     * @deprecated only valid for {@code POST /sessions}, which is now modelled by
     * {@link com.checkout.sessions.SessionChallengeIndicator#DATA_SHARE}. This value is rejected by
     * the {@code 3ds.challenge_indicator} fields that use this enum.
     */
    @Deprecated
    @SerializedName("data_share")
    DATA_SHARE,

}
