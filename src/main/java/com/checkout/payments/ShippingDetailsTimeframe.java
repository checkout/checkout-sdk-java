package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum ShippingDetailsTimeframe {

    @SerializedName("ElectronicDelivery")
    ELECTRONIC_DELIVERY,

    @SerializedName("SameDay")
    SAME_DAY,

    @SerializedName("Overnight")
    OVERNIGHT,

    @SerializedName("TwoDayOrMore")
    TWO_DAY_OR_MORE
}
