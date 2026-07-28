package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

public enum CompanyPosition {

    @SerializedName("ceo")
    CEO,
    @SerializedName("cfo")
    CFO,
    @SerializedName("coo")
    COO,
    @SerializedName("managing_member")
    MANAGING_MEMBER,
    @SerializedName("general_partner")
    GENERAL_PARTNER,
    @SerializedName("president")
    PRESIDENT,
    @SerializedName("vice_president")
    VICE_PRESIDENT,
    @SerializedName("treasurer")
    TREASURER,
    @SerializedName("other_senior_management")
    OTHER_SENIOR_MANAGEMENT,
    @SerializedName("other_executive_officer")
    OTHER_EXECUTIVE_OFFICER,
    @SerializedName("other_non_executive_non_senior")
    OTHER_NON_EXECUTIVE_NON_SENIOR
}
