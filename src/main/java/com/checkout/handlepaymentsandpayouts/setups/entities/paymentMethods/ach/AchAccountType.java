package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach;

import com.google.gson.annotations.SerializedName;

/**
 * The type of Direct Debit account.
 */
public enum AchAccountType {

    @SerializedName("savings")
    SAVINGS,

    @SerializedName("current")
    CURRENT,

    @SerializedName("cash")
    CASH
}
