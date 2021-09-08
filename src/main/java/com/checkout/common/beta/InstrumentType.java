package com.checkout.common.beta;

import com.google.gson.annotations.SerializedName;

public enum InstrumentType {

    @SerializedName("bank_account")
    BANK_ACCOUNT,

    @SerializedName("token")
    TOKEN,

    @SerializedName("card")
    CARD

}
