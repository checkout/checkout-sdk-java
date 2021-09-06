package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum PaymentSourceType {

    @SerializedName("card")
    CARD,
    @SerializedName("id")
    ID,
    @SerializedName("network_token")
    NETWORK_TOKEN,
    @SerializedName("token")
    TOKEN,
    @SerializedName("customer")
    CUSTOMER

}
