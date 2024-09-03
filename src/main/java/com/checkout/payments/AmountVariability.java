package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum AmountVariability {

    @SerializedName("Fixed")
    FIXED,

    @SerializedName("Variable")
    VARIABLE

}
