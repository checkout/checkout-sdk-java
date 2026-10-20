package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.googlepay;

import com.google.gson.annotations.SerializedName;

/**
 * A card scheme Google Pay accepts as the preferred scheme.
 */
public enum GooglePayPreferredScheme {

    @SerializedName("visa")
    VISA,

    @SerializedName("mastercard")
    MASTERCARD
}
