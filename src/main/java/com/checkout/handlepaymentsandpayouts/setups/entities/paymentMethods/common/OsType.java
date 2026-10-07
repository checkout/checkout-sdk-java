package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;

/**
 * The operating system of the customer's device. Used by the wallet payment methods that take an
 * os_type (required there when terminal_type is not web) and by the customer device os.
 */
public enum OsType {

    /**
     * Android.
     */
    @SerializedName("android")
    ANDROID,

    /**
     * iOS.
     */
    @SerializedName("ios")
    IOS
}
