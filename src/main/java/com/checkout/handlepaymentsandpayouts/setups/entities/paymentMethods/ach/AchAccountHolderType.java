package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach;

import com.google.gson.annotations.SerializedName;

/**
 * The type of account holder.
 */
public enum AchAccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,

    @SerializedName("corporate")
    CORPORATE,

    @SerializedName("government")
    GOVERNMENT
}
