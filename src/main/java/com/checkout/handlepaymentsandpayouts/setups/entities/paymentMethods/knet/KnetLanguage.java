package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.knet;

import com.google.gson.annotations.SerializedName;

/**
 * The customer's preferred language for KNET payments.
 */
public enum KnetLanguage {

    @SerializedName("en")
    EN,

    @SerializedName("ar")
    AR
}
