package com.checkout.risk.preauthentication;

import com.google.gson.annotations.SerializedName;

public enum PreAuthenticationDecision {

    @SerializedName("try_exemptions")
    TRY_EXEMPTIONS,
    @SerializedName("try_frictionless")
    TRY_FRICTIONLESS,
    @SerializedName("no_preference")
    NO_PREFERENCE,
    @SerializedName("force_challenge")
    FORCE_CHALLENGE,
    @SerializedName("decline")
    DECLINE

}
