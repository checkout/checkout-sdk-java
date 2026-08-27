package com.checkout.common;

import com.google.gson.annotations.SerializedName;

/**
 * The type of payment instrument.
 */
public enum InstrumentType {

    @SerializedName("bank_account")
    BANK_ACCOUNT,

    @SerializedName("token")
    TOKEN,

    @SerializedName("card")
    CARD,

    /**
     * Retained for the previous-platform instruments API. Not a value of the current platform's
     * instrument type.
     */
    @SerializedName("card_token")
    CARD_TOKEN,

    @SerializedName("sepa")
    SEPA,

    @SerializedName("ach")
    ACH,

    @SerializedName("bacs")
    BACS

}
