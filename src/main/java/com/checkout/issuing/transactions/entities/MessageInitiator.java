package com.checkout.issuing.transactions.entities;

import com.google.gson.annotations.SerializedName;

public enum MessageInitiator {
    @SerializedName("cardholder")
    CARDHOLDER,

    @SerializedName("merchant")
    MERCHANT,

    @SerializedName("acquirer")
    ACQUIRER,

    @SerializedName("card_network")
    CARD_NETWORK,

    @SerializedName("issuer")
    ISSUER
}