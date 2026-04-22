package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;

/**
 * The card account holder type.
 */
public enum CardAccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,

    @SerializedName("government")
    GOVERNMENT,

    @SerializedName("corporate")
    CORPORATE
}
