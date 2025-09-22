package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.individualaccountholder;

import com.google.gson.annotations.SerializedName;

public enum AccountNameInquiryType {

    @SerializedName("full_match")
    FULL_MATCH,

    @SerializedName("partial_match")
    PARTIAL_MATCH,

    @SerializedName("no_match")
    NO_MATCH,

    @SerializedName("not_performed")
    NOT_PERFORMED,

    @SerializedName("not_supported")
    NOT_SUPPORTED,

}
