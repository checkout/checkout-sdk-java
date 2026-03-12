package com.checkout.standaloneaccountupdater.entities;

import com.google.gson.annotations.SerializedName;

public enum AccountUpdateFailureCode {
    @SerializedName("CARDHOLDER_OPT_OUT")
    CARDHOLDER_OPT_OUT,

    @SerializedName("UP_TO_DATE")
    UP_TO_DATE,

    @SerializedName("NON_PARTICIPATING_BIN")
    NON_PARTICIPATING_BIN,
    
    @SerializedName("UNKNOWN")
    UNKNOWN
}