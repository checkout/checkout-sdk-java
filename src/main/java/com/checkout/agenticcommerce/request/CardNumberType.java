package com.checkout.agenticcommerce.request;

import com.google.gson.annotations.SerializedName;

public enum CardNumberType {

    /**
     * A Funding Primary Account Number. The card number printed on the card.
     */
    @SerializedName("fpan")
    FPAN,

    /**
     * A provisioned network token that represents the underlying card.
     */
    @SerializedName("network_token")
    NETWORK_TOKEN
}
