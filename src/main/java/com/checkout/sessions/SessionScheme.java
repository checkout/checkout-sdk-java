package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum SessionScheme {

    @SerializedName("visa")
    VISA,
    @SerializedName("mastercard")
    MASTERCARD,
    @SerializedName("jcb")
    JCB,
    @SerializedName("amex")
    AMEX,
    @SerializedName("diners")
    DINERS

}
