package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal;

import com.google.gson.annotations.SerializedName;

public enum PayPalShippingPreference {

    /**
     * Redacts the shipping address from the PayPal pages.
     */
    @SerializedName("no_shipping")
    NO_SHIPPING,

    /**
     * Obtains the shipping address from the customer's PayPal account.
     */
    @SerializedName("get_from_file")
    GET_FROM_FILE,

    /**
     * Uses the shipping address provided by the merchant.
     */
    @SerializedName("set_provided_address")
    SET_PROVIDED_ADDRESS

}
