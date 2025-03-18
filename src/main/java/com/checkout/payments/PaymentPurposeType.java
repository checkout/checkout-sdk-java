package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PaymentPurposeType {

    @SerializedName("donations")
    DONATIONS,

    @SerializedName("education")
    EDUCATION,

    @SerializedName("emergency_need")
    EMERGENCY_NEED,

    @SerializedName("expatriation")
    EXPATRIATION,

    @SerializedName("family_support")
    FAMILY_SUPPORT,

    @SerializedName("financial_services")
    FINANCIAL_SERVICES,

    @SerializedName("gifts")
    GIFTS,

    @SerializedName("income")
    INCOME,

    @SerializedName("insurance")
    INSURANCE,

    @SerializedName("investment")
    INVESTMENT,

    @SerializedName("it_services")
    IT_SERVICES,

    @SerializedName("leisure")
    LEISURE,

    @SerializedName("loan_payment")
    LOAN_PAYMENT,

    @SerializedName("medical_treatment")
    MEDICAL_TREATMENT,

    @SerializedName("other")
    OTHER,

    @SerializedName("pension")
    PENSION,

    @SerializedName("royalties")
    ROYALTIES,

    @SerializedName("savings")
    SAVINGS,

    @SerializedName("travel_and_tourism")
    TRAVEL_AND_TOURISM
}
