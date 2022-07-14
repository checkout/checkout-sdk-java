package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum ThreeDSEnrollmentStatus {

    @SerializedName("Y")
    ISSUER_ENROLLED,
    @SerializedName("N")
    CUSTOMER_NOT_ENROLLED,
    @SerializedName("U")
    UNKNOWN

}
