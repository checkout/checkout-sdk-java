package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination;

import com.google.gson.annotations.SerializedName;

public enum DestinationType {

    @SerializedName("token")
    TOKEN,

    @SerializedName("id")
    ID,

    @SerializedName("card")
    CARD,

    @SerializedName("network_token")
    NETWORK_TOKEN,

}
