package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;

/**
 * A card scheme name, as listed in the schemes of a Payment Setup card, Apple Pay or Google Pay
 * payment method.
 */
public enum PaymentSetupScheme {

    @SerializedName("accel")
    ACCEL,

    @SerializedName("amex")
    AMEX,

    @SerializedName("cartes_bancaires")
    CARTES_BANCAIRES,

    @SerializedName("china_union_pay")
    CHINA_UNION_PAY,

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
