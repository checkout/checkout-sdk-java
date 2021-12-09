package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum CardType {

    @SerializedName(value = "Credit", alternate = {"CREDIT"})
    CREDIT,
    @SerializedName(value = "Debit", alternate = {"DEBIT"})
    DEBIT,
    @SerializedName(value = "Prepaid", alternate = {"PREPAID"})
    PREPAID,
    @SerializedName(value = "Charge", alternate = {"CHARGE"})
    CHARGE,
    @SerializedName(value = "Deferred Debit", alternate = {"DEFERRED DEBIT"})
    DEFERRED_DEBIT

}
