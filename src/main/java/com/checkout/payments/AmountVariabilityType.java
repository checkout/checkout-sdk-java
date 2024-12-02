package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum AmountVariabilityType {

    @SerializedName("Fixed")
    FIXED,

    @SerializedName("Variable")
    VARIABLE

}
