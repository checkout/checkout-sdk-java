package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum InstrumentType {

    @SerializedName("bank_account")
    BANK_ACCOUNT,

    @SerializedName("token")
    TOKEN,

    @SerializedName("card")
    CARD,

    @SerializedName("card_token")
    CARD_TOKEN,

    @SerializedName("sepa")
    SEPA

}
