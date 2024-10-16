package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PaymentDestinationType {

    @SerializedName("card")
    CARD,
    @SerializedName("id")
    ID,
    @SerializedName("token")
    TOKEN,
    @SerializedName("network_token")
    NETWORK_TOKEN,
    @SerializedName("bank_account")
    BANK_ACCOUNT;

}
