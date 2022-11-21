package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;

public enum CardMetadataSourceType {

    @SerializedName("bin")
    BIN,
    @SerializedName("card")
    CARD,
    @SerializedName("token")
    TOKEN,
    @SerializedName("id")
    ID
}