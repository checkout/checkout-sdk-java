package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa;

import com.google.gson.annotations.SerializedName;

/**
 * The type of SEPA account holder.
 */
public enum SepaAccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,

    @SerializedName("corporate")
    CORPORATE
}
