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
    @SerializedName("customer")
    CUSTOMER,
    @SerializedName("dlocal")
    DLOCAL,
    @SerializedName("baloto")
    BALOTO,
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
    @SerializedName("sofort")
    SOFORT,
    @SerializedName("qpay")
    QPAY,
    @SerializedName("alipay")
    ALIPAY,
    @SerializedName("paypal")
    PAYPAL,
    @SerializedName("multibanco")
    MULTIBANCO,
    @SerializedName("eps")
    EPS,
    @SerializedName("poli")
    POLI,
    @SerializedName("p24")
    PRZELEWY24,
    @SerializedName("benefitpay")
    BENEFITPAY,
    @SerializedName("bancontact")
    BANCONTACT,
    @SerializedName("tamara")
    TAMARA
}
