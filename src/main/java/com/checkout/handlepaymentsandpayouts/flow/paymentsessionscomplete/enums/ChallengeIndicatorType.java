package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums;

import com.google.gson.annotations.SerializedName;

/** Default: "no_preference" Specifies the preference for whether a 3DS challenge should be performed. Ultimately,
 * whether the challenge is presented to the customer or not is up to their bank's discretion.
 */
public enum ChallengeIndicatorType {
    @SerializedName("no_preference")
    NO_PREFERENCE,
    @SerializedName("no_challenge_requested")
    NO_CHALLENGE_REQUESTED,
    @SerializedName("challenge_requested")
    CHALLENGE_REQUESTED,
    @SerializedName("challenge_requested_mandate")
    CHALLENGE_REQUESTED_MANDATE
}
