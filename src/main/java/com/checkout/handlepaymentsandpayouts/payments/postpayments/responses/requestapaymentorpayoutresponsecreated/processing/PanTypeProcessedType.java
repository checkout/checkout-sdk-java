package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.processing;

import com.google.gson.annotations.SerializedName;

public enum PanTypeProcessedType {

    @SerializedName("fpan")
    FPAN,

    @SerializedName("dpan")
    DPAN,

}
