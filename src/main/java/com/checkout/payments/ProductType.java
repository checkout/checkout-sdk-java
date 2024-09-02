package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum ProductType {

    @SerializedName("QR Code")
    QR_CODE,

    @SerializedName("In-App")
    IN_APP,

    @SerializedName("Official Account")
    OFFICIAL_ACCOUNT,

    @SerializedName("Mini Program")
    MINI_PROGRAM,

    @SerializedName("pay_in_full")
    PAY_IN_FULL,

    @SerializedName("pay_by_instalment")
    PAY_BY_INSTALMENT,

    @SerializedName("pay_by_instalment_2")
    PAY_BY_INSTALMENT_2,

    @SerializedName("pay_by_instalment_3")
    PAY_BY_INSTALMENT_3,

    @SerializedName("pay_by_instalment_4")
    PAY_BY_INSTALMENT_4,

    @SerializedName("pay_by_instalment_6")
    PAY_BY_INSTALMENT_6,

    @SerializedName("invoice")
    INVOICE,

    @SerializedName("pay_later")
    PAY_LATER

}