package com.checkout.issuing.cardholders;

import com.google.gson.annotations.SerializedName;

public enum CardholderStatus {

    @SerializedName("active")
    ACTIVE,
    @SerializedName("pending")
    PENDING,
    @SerializedName("restricted")
    RESTRICTED,
    @SerializedName("requirements_due")
    REQUIREMENTS_DUE,
    @SerializedName("inactive")
    INACTIVE,
    @SerializedName("rejected")
    REJECTED

}
