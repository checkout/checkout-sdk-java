package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;

/**
 * The client-side terminal type. Indicates whether the customer is using a PC browser,
 * mobile browser, or mobile application.
 */
public enum TerminalType {

    @SerializedName("web")
    WEB,

    @SerializedName("wap")
    WAP,

    @SerializedName("app")
    APP
}
