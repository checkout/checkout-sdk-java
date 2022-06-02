package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum DeliveryTimeframe {

    @SerializedName("electronic_delivery")
    ELECTRONIC_DELIVERY,
    @SerializedName("same_day")
    SAME_DAY,
    @SerializedName("overnight")
    OVERNIGHT,
    @SerializedName("two_day_or_more")
    TWO_DAY_OR_MORE

}
