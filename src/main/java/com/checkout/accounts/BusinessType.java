package com.checkout.accounts;

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
    AUTO_ENTREPRENEUR,
    @SerializedName("individual_or_sole_proprietorship")
    INDIVIDUAL_OR_SOLE_PROPRIETORSHIP,
    @SerializedName("scottish_limited_partnership")
    SCOTTISH_LIMITED_PARTNERSHIP,
    @SerializedName("limited_liability_corporation")
    LIMITED_LIABILITY_CORPORATION,
    @SerializedName("private_corporation")
    PRIVATE_CORPORATION,
    @SerializedName("publicly_traded_corporation")
    PUBLICLY_TRADED_CORPORATION,
    @SerializedName("government_agency")
    GOVERNMENT_AGENCY,
    @SerializedName("non_profit_entity")
    NON_PROFIT_ENTITY,
    @SerializedName("trust")
    TRUST,
    @SerializedName("club_or_society")
    CLUB_OR_SOCIETY,
    @SerializedName("regulated_financial_institution")
    REGULATED_FINANCIAL_INSTITUTION,
    @SerializedName("cftc_registered_entity")
    CFTC_REGISTERED_ENTITY,
    @SerializedName("sec_registered_entity")
    SEC_REGISTERED_ENTITY
}
