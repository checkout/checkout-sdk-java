package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;

/**
 * The customer's operating system type. Required when terminal_type is not web.
 */
public enum OsType {

    @SerializedName("android")
    ANDROID,

    @SerializedName("ios")
    IOS
}
