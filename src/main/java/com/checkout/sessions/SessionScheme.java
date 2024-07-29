package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum SessionScheme {

    @SerializedName("amex")
    AMEX,
    @SerializedName("cartes_bancaires")
    CARTES_BANCAIRES,
    @SerializedName("diners")
    DINERS,
    @SerializedName("jcb")
    JCB,
    @SerializedName("mastercard")
    MASTERCARD,
    @SerializedName("visa")
    VISA

}
