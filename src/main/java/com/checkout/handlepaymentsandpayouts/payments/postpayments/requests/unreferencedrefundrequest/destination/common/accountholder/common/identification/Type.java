package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.common.identification;

import com.google.gson.annotations.SerializedName;

public enum Type {

    @SerializedName("passport")
    PASSPORT,

    @SerializedName("driving_license")
    DRIVING_LICENSE,

    @SerializedName("national_id")
    NATIONAL_ID,

    @SerializedName("company_registration")
    COMPANY_REGISTRATION,

    @SerializedName("tax_id")
    TAX_ID,

}
