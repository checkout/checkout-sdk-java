package com.checkout.customers.beta.instrument;

import com.google.gson.annotations.SerializedName;

public enum CustomerInstrumentType {

    @SerializedName("card")
    CARD,
    @SerializedName("bank_account")
    BANK_ACCOUNT,

}
