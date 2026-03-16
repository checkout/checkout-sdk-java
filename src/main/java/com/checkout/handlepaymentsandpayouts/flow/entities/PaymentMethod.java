package com.checkout.handlepaymentsandpayouts.flow.entities;

import com.google.gson.annotations.SerializedName;

public enum PaymentMethod {
    @SerializedName("alipay_cn")
    ALIPAY_CN,

    @SerializedName("alipay_hk")
    ALIPAY_HK,

    @SerializedName("alma")
    ALMA,

    @SerializedName("applepay")
    APPLEPAY,

    @SerializedName("bancontact")
    BANCONTACT,

    @SerializedName("benefit")
    BENEFIT,

    @SerializedName("bizum")
    BIZUM,

    @SerializedName("card")
    CARD,

    @SerializedName("dana")
    DANA,

    @SerializedName("eps")
    EPS,

    @SerializedName("gcash")
    GCASH,

    @SerializedName("googlepay")
    GOOGLEPAY,

    @SerializedName("ideal")
    IDEAL,

    @SerializedName("kakaopay")
    KAKAOPAY,

    @SerializedName("klarna")
    KLARNA,

    @SerializedName("knet")
    KNET,

    @SerializedName("mbway")
    MBWAY,

    @SerializedName("mobilepay")
    MOBILEPAY,

    @SerializedName("multibanco")
    MULTIBANCO,

    @SerializedName("octopus")
    OCTOPUS,

    @SerializedName("p24")
    P24,

    @SerializedName("paynow")
    PAYNOW,

    @SerializedName("paypal")
    PAYPAL,

    @SerializedName("plaid")
    PLAID,

    @SerializedName("qpay")
    QPAY,

    REMEMBER_ME,

    @SerializedName("sepa")
    SEPA,

    @SerializedName("stcpay")
    STCPAY,

    STORED_CARD,

    @SerializedName("tabby")
    TABBY,

    @SerializedName("tamara")
    TAMARA,

    @SerializedName("tng")
    TNG,

    @SerializedName("truemoney")
    TRUEMONEY,

    @SerializedName("twint")
    TWINT,

    @SerializedName("vipps")
    VIPPS,

    @SerializedName("wechatpay")
    WECHATPAY
}