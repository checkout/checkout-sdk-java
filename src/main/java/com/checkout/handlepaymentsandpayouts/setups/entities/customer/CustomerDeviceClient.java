package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import com.google.gson.annotations.SerializedName;

/**
 * The type of client the customer uses to initiate the payment.
 */
public enum CustomerDeviceClient {

    /**
     * A web browser on a desktop device.
     */
    @SerializedName("web")
    WEB,

    /**
     * A web browser on a mobile device.
     */
    @SerializedName("mobile_web")
    MOBILE_WEB,

    /**
     * A native mobile application.
     */
    @SerializedName("app")
    APP
}
