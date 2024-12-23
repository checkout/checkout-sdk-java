package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum PaymentSourceType {

    @SerializedName("card")
    CARD,
    @SerializedName("id")
    ID,
    @SerializedName("currency_account")
    CURRENCY_ACCOUNT,
    @SerializedName("network_token")
    NETWORK_TOKEN,
    @SerializedName("token")
    TOKEN,
    @SerializedName("ach")
    ACH,
    @SerializedName("customer")
    CUSTOMER,
    @SerializedName("applepay")
    APPLEPAY,
    @SerializedName("dlocal")
    DLOCAL,
    @SerializedName("boleto")
    BOLETO,
    @SerializedName("fawry")
    FAWRY,
    @SerializedName("giropay")
    GIROPAY,
    @SerializedName("ideal")
    IDEAL,
    @SerializedName("oxxo")
    OXXO,
    @SerializedName("pagofacil")
    PAGOFACIL,
    @SerializedName("rapipago")
    RAPIPAGO,
    @SerializedName("klarna")
    KLARNA,
    @SerializedName("id")
    SEPA,
    @SerializedName("sepa")
    SEPAV4,
    @SerializedName("sofort")
    SOFORT,
    @SerializedName("qpay")
    QPAY,
    @SerializedName("alipay")
    ALIPAY,
    @SerializedName("googlepay")
    GOOGLEPAY,
    @SerializedName("paypal")
    PAYPAL,
    @SerializedName("multibanco")
    MULTIBANCO,
    @SerializedName("octopus")
    OCTOPUS,
    @SerializedName("plaid")
    PLAID,
    @SerializedName("eps")
    EPS,
    @SerializedName("illicado")
    ILLICADO,
    @SerializedName("poli")
    POLI,
    @SerializedName("p24")
    P24,
    @SerializedName("benefitpay")
    BENEFITPAY,
    @SerializedName("bizum")
    BIZUM,
    @SerializedName("bancontact")
    BANCONTACT,
    @SerializedName("tamara")
    TAMARA,
    @SerializedName("provider_token")
    PROVIDER_TOKEN,
    @SerializedName("knet")
    KNET,
    @SerializedName("bank_account")
    BANK_ACCOUNT,
    @SerializedName("alipay_hk")
    ALIPAY_HK,
    @SerializedName("alipay_cn")
    ALIPAY_CN,
    @SerializedName("alipay_plus")
    ALIPAY_PLUS,
    @SerializedName("gcash")
    GCASH,
    @SerializedName("wechatpay")
    WECHATPAY,
    @SerializedName("dana")
    DANA,
    @SerializedName("kakaopay")
    KAKAOPAY,
    @SerializedName("truemoney")
    TRUEMONEY,
    @SerializedName("tng")
    TNG,
    @SerializedName("afterpay")
    AFTERPAY,
    @SerializedName("benefit")
    BENEFIT,
    @SerializedName("mbway")
    MBWAY,
    @SerializedName("postfinance")
    POSTFINANCE,
    @SerializedName("stcpay")
    STCPAY,
    @SerializedName("alma")
    ALMA,
    @SerializedName("trustly")
    TRUSTLY,
    @SerializedName("cvconnect")
    CV_CONNECT,
    @SerializedName("sequra")
    SEQURA,
    @SerializedName("tabby")
    TABBY
}
