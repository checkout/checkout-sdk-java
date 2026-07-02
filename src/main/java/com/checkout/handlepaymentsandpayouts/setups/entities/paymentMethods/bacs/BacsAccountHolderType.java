package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs;

import com.google.gson.annotations.SerializedName;

/**
 * The type of account holder.
 */
public enum BacsAccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,

    @SerializedName("corporate")
    CORPORATE
}
