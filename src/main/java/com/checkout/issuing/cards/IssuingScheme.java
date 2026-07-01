package com.checkout.issuing.cards;

import com.google.gson.annotations.SerializedName;

/**
 * The card scheme.
 */
public enum IssuingScheme {

    @SerializedName("mastercard")
    MASTERCARD,

    @SerializedName("visa")
    VISA
}
