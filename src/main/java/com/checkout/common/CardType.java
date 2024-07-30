package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum CardType {

    @SerializedName(value = "Charge", alternate = {"CHARGE", "charge"})
    CHARGE,
    @SerializedName(value = "Credit", alternate = {"CREDIT", "credit"})
    CREDIT,
    @SerializedName(value = "Debit", alternate = {"DEBIT", "debit"})
    DEBIT,
    @SerializedName(value = "Deferred Debit", alternate = {"DEFERRED DEBIT", "deferred_debit"})
    DEFERRED_DEBIT,
    @SerializedName(value = "Prepaid", alternate = {"PREPAID", "prepaid"})
    PREPAID,

}
