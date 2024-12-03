package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;

public enum TrackingInformationType {

    @SerializedName("in_transit")
    IN_TRANSIT,

    @SerializedName("partial_shipped")
    PARTIAL_SHIPPED,

    @SerializedName("shipped")
    SHIPPED,

    @SerializedName("shipping_exception")
    SHIPPING_EXCEPTION,

    @SerializedName("delivered")
    DELIVERED,

    @SerializedName("other")
    OTHER
}
