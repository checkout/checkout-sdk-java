package com.checkout.payments.response;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationExperience {

    @SerializedName("google_spa")
    GOOGLE_SPA,

    @SerializedName("3ds")
    THREE_DS

}
