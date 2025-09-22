package com.checkout.handlepaymentsandpayouts.payments.common.source;

import com.google.gson.annotations.SerializedName;

public enum SourceType {

    @SerializedName("card")
    CARD,

    @SerializedName("afterpay")
    AFTERPAY,

    @SerializedName("alipay_cn")
    ALIPAY_CN,

    @SerializedName("alipay_hk")
    ALIPAY_HK,

    @SerializedName("alipay_plus")
    ALIPAY_PLUS,

    @SerializedName("alma")
    ALMA,

    @SerializedName("bancontact")
    BANCONTACT,

    @SerializedName("benefit")
    BENEFIT,

    @SerializedName("cvconnect")
    CVCONNECT,

    @SerializedName("dana")
    DANA,

    @SerializedName("eps")
    EPS,

    @SerializedName("fawry")
    FAWRY,

    @SerializedName("gcash")
    GCASH,

    @SerializedName("ideal")
    IDEAL,

    @SerializedName("illicado")
    ILLICADO,

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

    @SerializedName("paynow")
    PAYNOW,

    @SerializedName("paypal")
    PAYPAL,

    @SerializedName("postfinance")
    POSTFINANCE,

    @SerializedName("p24")
    P24,

    @SerializedName("qpay")
    QPAY,

    @SerializedName("sepa")
    SEPA,

    @SerializedName("sequra")
    SEQURA,

    @SerializedName("stcpay")
    STCPAY,

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
    WECHATPAY,

    @SerializedName("currency_account")
    CURRENCY_ACCOUNT,

    @SerializedName("PaymentGetResponseGiropaySource")
    PAYMENT_GET_RESPONSE_GIROPAY_SOURCE,

    @SerializedName("PaymentGetResponseKlarnaSource")
    PAYMENT_GET_RESPONSE_KLARNA_SOURCE,

    @SerializedName("PaymentGetResponseSEPAV4Source")
    PAYMENT_GET_RESPONSE_SEPAV4_SOURCE,

    @SerializedName("PaymentResponseSource")
    PAYMENT_RESPONSE_SOURCE,

}
