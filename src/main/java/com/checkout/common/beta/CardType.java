package com.checkout.common.beta;

import com.google.gson.annotations.SerializedName;

public enum CardType {

    @SerializedName("Credit")
    CREDIT,
    @SerializedName("Debit")
    DEBIT,
    @SerializedName("Prepaid")
    PREPAID,
    @SerializedName("Charge")
    CHARGE,
    @SerializedName("Deferred Debit")
    DEFERRED_DEBIT

}
