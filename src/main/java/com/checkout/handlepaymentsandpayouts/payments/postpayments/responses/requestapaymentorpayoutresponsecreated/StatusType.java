package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated;

import com.google.gson.annotations.SerializedName;

public enum StatusType {

    @SerializedName("Authorized")
    AUTHORIZED,

    @SerializedName("Pending")
    PENDING,

    @SerializedName("Card Verified")
    CARD_VERIFIED,

    @SerializedName("Declined")
    DECLINED,

    @SerializedName("Retry Scheduled")
    RETRY_SCHEDULED,

}
