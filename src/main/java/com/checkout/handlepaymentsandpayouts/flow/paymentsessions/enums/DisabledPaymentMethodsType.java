package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums;

import com.google.gson.annotations.SerializedName;

/** Specifies which payment method options to not present to the customer. If you specify the same payment method in
 * this field and in enabled_payment_methods, the disabled_payment_methods value will be overridden. Any payment method
 * options not explicitly specified in this field will be presented to the customer by default.
 */
public enum DisabledPaymentMethodsType {
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
    @SerializedName("multibanco")
    MULTIBANCO,
    @SerializedName("p24")
    P24,
    @SerializedName("paypal")
    PAYPAL,
    @SerializedName("qpay")
    QPAY,
    @SerializedName("sepa")
    SEPA,
    @SerializedName("sofort")
    SOFORT,
    @SerializedName("stcpay")
    STCPAY,
    @SerializedName("tabby")
    TABBY,
    @SerializedName("tamara")
    TAMARA,
    @SerializedName("tng")
    TNG,
    @SerializedName("truemoney")
    TRUEMONEY
}
