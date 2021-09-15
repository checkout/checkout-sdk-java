package com.checkout.common.four;

import com.google.gson.annotations.SerializedName;

public enum AccountType {

    @SerializedName("savings")
    SAVINGS,
    @SerializedName("current")
    CURRENT,
    @SerializedName("cash")
    CASH

}
