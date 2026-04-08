package com.checkout.agenticcommerce.request;

import com.google.gson.annotations.SerializedName;

public enum CardFundingType {

    @SerializedName("credit")
    CREDIT,

    @SerializedName("debit")
    DEBIT,

    @SerializedName("prepaid")
    PREPAID
}
