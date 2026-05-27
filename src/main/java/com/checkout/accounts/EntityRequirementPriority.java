package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

public enum EntityRequirementPriority {

    @SerializedName("high")
    HIGH,

    @SerializedName("critical")
    CRITICAL
}
