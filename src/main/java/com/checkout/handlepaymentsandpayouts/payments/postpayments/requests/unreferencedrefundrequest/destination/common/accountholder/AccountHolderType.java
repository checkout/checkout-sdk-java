package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder;

import com.google.gson.annotations.SerializedName;

public enum AccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,

    @SerializedName("corporate")
    CORPORATE,

    @SerializedName("government")
    GOVERNMENT,

}
