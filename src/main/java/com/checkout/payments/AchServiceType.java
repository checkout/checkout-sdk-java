package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum AchServiceType {

    @SerializedName("same_day")
    SAME_DAY,

    @SerializedName("standard")
    STANDARD

}
