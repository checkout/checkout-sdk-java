package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;

public enum CardMetadataFormatType {

    @SerializedName("basic")
    BASIC,
    @SerializedName("card_payouts")
    CARD_PAYOUTS
}