package com.checkout.issuing.cards;

import com.google.gson.annotations.SerializedName;

public enum CardType {

    @SerializedName("physical")
    PHYSICAL,
    @SerializedName("virtual")
    VIRTUAL

}
