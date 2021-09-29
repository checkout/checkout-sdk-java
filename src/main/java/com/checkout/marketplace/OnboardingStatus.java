package com.checkout.marketplace;

import com.google.gson.annotations.SerializedName;

public enum OnboardingStatus {

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
