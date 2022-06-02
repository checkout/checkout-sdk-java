package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PreferredSchema {

    @SerializedName("visa")
    VISA,
    @SerializedName("mastercard")
    MASTERCARD,
    @SerializedName("cartes_bancaires")
    CARTES_BANCAIRES

}