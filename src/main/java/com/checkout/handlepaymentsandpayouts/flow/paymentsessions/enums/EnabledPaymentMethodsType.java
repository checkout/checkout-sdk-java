package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums;

import com.google.gson.annotations.SerializedName;

/** Specifies which payment method options to present to the customer. The values in this field override any equivalent
 * values in disabled_payment_methods.
 */
public enum EnabledPaymentMethodsType {
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
