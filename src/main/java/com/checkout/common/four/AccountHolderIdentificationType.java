package com.checkout.common.four;

import com.google.gson.annotations.SerializedName;

public enum AccountHolderIdentificationType {

    @SerializedName("passport")
    PASSPORT,

    @SerializedName("driving_licence")
    DRIVING_LICENCE,

    @SerializedName("national_id")
    NATIONAL_ID,

    @SerializedName("company_registration")
    COMPANY_REGISTRATION,

    @SerializedName("tax_id")
    TAX_ID
}
