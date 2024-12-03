package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;

public enum ShippingDeliveryStatusType {

    @SerializedName("not_shipped")
    NOT_SHIPPED,

    @SerializedName("back_ordered")
    BACK_ORDERED,

    @SerializedName("in_transit")
    IN_TRANSIT,

    @SerializedName("partial_shipped")
    PARTIAL_SHIPPED,

    @SerializedName("shipped")
    SHIPPED,

    @SerializedName("cancelled")
    CANCELLED,

    @SerializedName("shipping_exception")
    SHIPPING_EXCEPTION,

    @SerializedName("picked_up_by_customer")
    PICKED_UP_BY_CUSTOMER,

    @SerializedName("delivered")
    DELIVERED,

    @SerializedName("other")
    OTHER
}
