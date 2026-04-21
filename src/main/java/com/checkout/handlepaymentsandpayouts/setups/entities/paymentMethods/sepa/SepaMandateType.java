package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa;

import com.google.gson.annotations.SerializedName;

/**
 * The type of SEPA mandate.
 */
public enum SepaMandateType {

    @SerializedName("core")
    CORE,

    @SerializedName("b2b")
    B2B
}
