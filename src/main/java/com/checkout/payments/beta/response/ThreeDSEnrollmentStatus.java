package com.checkout.payments.beta.response;

import com.google.gson.annotations.SerializedName;

public enum ThreeDSEnrollmentStatus {

    @SerializedName("Y")
    YES,
    @SerializedName("N")
    NO,
    @SerializedName("U")
    U

}
