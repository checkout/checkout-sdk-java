package com.checkout.common.beta;

import com.google.gson.annotations.SerializedName;

public enum AccountType {

    @SerializedName("savings")
    SAVINGS,
    @SerializedName("current")
    CURRENT,
    @SerializedName("cash")
    CASH

}
