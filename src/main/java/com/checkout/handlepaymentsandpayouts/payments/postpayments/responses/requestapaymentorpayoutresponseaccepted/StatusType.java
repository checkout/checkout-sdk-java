package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted;

import com.google.gson.annotations.SerializedName;

public enum StatusType {

    @SerializedName("Accepted")
    ACCEPTED,

    @SerializedName("Rejected")
    REJECTED,

    @SerializedName("Pending")
    PENDING,

}
