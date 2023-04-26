package com.checkout.issuing.cards;

import com.google.gson.annotations.SerializedName;

public enum CardStatus {

    @SerializedName("active")
    ACTIVE,
    @SerializedName("inactive")
    INACTIVE,
    @SerializedName("revoked")
    REVOKED,
    @SerializedName("suspended")
    SUSPENDED

}
