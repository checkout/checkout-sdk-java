package com.checkout.marketplace;

import com.google.gson.annotations.SerializedName;

public enum InstrumentStatus {

    @SerializedName("verified")
    VERIFIED,
    @SerializedName("unverified")
    UNVERIFIED,
    @SerializedName("pending")
    PENDING

}
