package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.individualaccountholder.accountnameinquirydetails;

import com.google.gson.annotations.SerializedName;

public enum LastNameType {

    @SerializedName("full_match")
    FULL_MATCH,

    @SerializedName("partial_match")
    PARTIAL_MATCH,

    @SerializedName("no_match")
    NO_MATCH,

}
