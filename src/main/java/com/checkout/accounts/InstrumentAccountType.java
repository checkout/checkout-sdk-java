package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * The type of bank account.
 */
public enum InstrumentAccountType {

    @SerializedName("savings")
    SAVINGS,

    @SerializedName("checking")
    CHECKING
}
