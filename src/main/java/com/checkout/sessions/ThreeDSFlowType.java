package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum ThreeDSFlowType {

    @SerializedName("challenged")
    CHALLENGED,
    @SerializedName("frictionless")
    FRICTIONLESS,
    @SerializedName("frictionless_delegated")
    FRICTIONLESS_DELEGATED

}
