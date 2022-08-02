package com.checkout.marketplace;

import com.google.gson.annotations.SerializedName;

public enum BusinessType {

    @SerializedName("general_partnership")
    GENERAL_PARTNERSHIP,
    @SerializedName("limited_partnership")
    LIMITED_PARTNERSHIP,
    @SerializedName("public_limited_company")
    PUBLIC_LIMITED_COMPANY,
    @SerializedName("limited_company")
    LIMITED_COMPANY,
    @SerializedName("professional_association")
    PROFESSIONAL_ASSOCIATION,
    @SerializedName("unincorporated_association")
    UNINCORPORATED_ASSOCIATION,
    @SerializedName("auto_entrepreneur")
    AUTO_ENTREPRENEUR
}
