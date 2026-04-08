package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PaymentRoutingScheme {

    @SerializedName("accel")
    ACCEL,

    @SerializedName("amex")
    AMEX,

    @SerializedName("cartes_bancaires")
    CARTES_BANCAIRES,

    @SerializedName("diners")
    DINERS,

    @SerializedName("discover")
    DISCOVER,

    @SerializedName("jcb")
    JCB,

    @SerializedName("mada")
    MADA,

    @SerializedName("maestro")
    MAESTRO,

    @SerializedName("mastercard")
    MASTERCARD,

    @SerializedName("nyce")
    NYCE,

    @SerializedName("omannet")
    OMANNET,

    @SerializedName("pulse")
    PULSE,

    @SerializedName("shazam")
    SHAZAM,

    @SerializedName("star")
    STAR,

    @SerializedName("upi")
    UPI,

    @SerializedName("visa")
    VISA

}
