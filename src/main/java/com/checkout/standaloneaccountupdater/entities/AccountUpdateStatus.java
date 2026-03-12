package com.checkout.standaloneaccountupdater.entities;

import com.google.gson.annotations.SerializedName;

public enum AccountUpdateStatus {
    @SerializedName("CARD_UPDATED")
    CARD_UPDATED,

    @SerializedName("CARD_EXPIRY_UPDATED")
    CARD_EXPIRY_UPDATED,

    @SerializedName("CARD_CLOSED")
    CARD_CLOSED,
    
    @SerializedName("UPDATE_FAILED")
    UPDATE_FAILED
}