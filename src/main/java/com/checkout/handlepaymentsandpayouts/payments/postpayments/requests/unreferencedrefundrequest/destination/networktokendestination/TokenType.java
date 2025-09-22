package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.networktokendestination;

import com.google.gson.annotations.SerializedName;

public enum TokenType {

    @SerializedName("vts")
    VTS,

    @SerializedName("mdes")
    MDES,

    @SerializedName("applepay")
    APPLEPAY,

    @SerializedName("googlepay")
    GOOGLEPAY,

}
