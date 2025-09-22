package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.instruction;

import com.google.gson.annotations.SerializedName;

public enum PurposeType {

    @SerializedName("family_support")
    FAMILY_SUPPORT,

    @SerializedName("expatriation")
    EXPATRIATION,

    @SerializedName("travel_and_tourism")
    TRAVEL_AND_TOURISM,

    @SerializedName("education")
    EDUCATION,

    @SerializedName("medical_treatment")
    MEDICAL_TREATMENT,

    @SerializedName("emergency_need")
    EMERGENCY_NEED,

    @SerializedName("leisure")
    LEISURE,

    @SerializedName("savings")
    SAVINGS,

    @SerializedName("gifts")
    GIFTS,

    @SerializedName("donations")
    DONATIONS,

    @SerializedName("financial_services")
    FINANCIAL_SERVICES,

    @SerializedName("it_services")
    IT_SERVICES,

    @SerializedName("investment")
    INVESTMENT,

    @SerializedName("insurance")
    INSURANCE,

    @SerializedName("loan_payment")
    LOAN_PAYMENT,

    @SerializedName("pension")
    PENSION,

    @SerializedName("royalties")
    ROYALTIES,

    @SerializedName("other")
    OTHER,

    @SerializedName("income")
    INCOME,

}
