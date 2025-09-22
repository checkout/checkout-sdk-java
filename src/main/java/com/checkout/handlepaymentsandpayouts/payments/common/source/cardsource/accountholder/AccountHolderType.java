package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder;

import com.google.gson.annotations.SerializedName;

public enum AccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,

    @SerializedName("corporate")
    CORPORATE,

    @SerializedName("government")
    GOVERNMENT,

}
