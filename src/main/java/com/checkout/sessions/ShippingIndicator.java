package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum ShippingIndicator {

    @SerializedName("billing_address")
    BILLING_ADDRESS,
    @SerializedName("another_address_on_file")
    ANOTHER_ADDRESS_ON_FILE,
    @SerializedName("not_on_file")
    NOT_ON_FILE,
    @SerializedName("store_pick_up")
    STORE_PICK_UP,
    @SerializedName("digital_goods")
    DIGITAL_GOODS,
    @SerializedName("travel_and_event_no_shipping")
    TRAVEL_AND_EVENT_NO_SHIPPING,
    @SerializedName("other")
    OTHER

}
