package com.checkout.networktokens.entities;

import com.google.gson.annotations.SerializedName;

public enum NetworkTokenSourceType {
    @SerializedName("id")
    ID,

    @SerializedName("card")
    CARD
}