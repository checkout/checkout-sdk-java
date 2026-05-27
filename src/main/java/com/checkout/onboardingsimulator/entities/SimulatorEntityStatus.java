package com.checkout.onboardingsimulator.entities;

import com.google.gson.annotations.SerializedName;

public enum SimulatorEntityStatus {

    @SerializedName("draft")
    DRAFT,

    @SerializedName("requirements_due")
    REQUIREMENTS_DUE,

    @SerializedName("pending")
    PENDING,

    @SerializedName("active")
    ACTIVE,

    @SerializedName("restricted")
    RESTRICTED,

    @SerializedName("rejected")
    REJECTED,

    @SerializedName("inactive")
    INACTIVE
}
