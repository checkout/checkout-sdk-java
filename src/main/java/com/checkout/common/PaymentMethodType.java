package com.checkout.common;

import com.google.gson.annotations.SerializedName;

/**
 * The type of payment method.
 *
 * <p>This is a consolidated enum covering every payment method type the SDK sees, across
 * GET /payment-methods, the flow API and the payment session entities. The specification's
 * PaymentMethod.type enum is a subset of it, so the constants declared here that the current
 * specification does not list are deliberate rather than invented, and must not be removed by a
 * reverse comparison against a single endpoint.
 */
public enum PaymentMethodType {

    @SerializedName("accel")
    ACCEL,
    @SerializedName("ach")
    ACH,
    @SerializedName("alipay_cn")
    ALIPAY_CN,
    @SerializedName("alipay_hk")
    ALIPAY_HK,
    @SerializedName("alipay_plus")
    ALIPAY_PLUS,
    @SerializedName("alma")
    ALMA,
    @SerializedName("amex")
    AMEX,
    @SerializedName("applepay")
    APPLEPAY,
    @SerializedName("bacs")
    BACS,
    @SerializedName("bancontact")
    BANCONTACT,
    @SerializedName("benefit")
    BENEFIT,
    @SerializedName("bizum")
    BIZUM,
    @SerializedName("blik")
    BLIK,
    @SerializedName("boost")
    BOOST,
    @SerializedName("bpi")
    BPI,
    @SerializedName("card")
    CARD,
    @SerializedName("cartes_bancaires")
    CARTES_BANCAIRES,
    @SerializedName("china_union_pay")
    CHINA_UNION_PAY,
    @SerializedName("connect_wallet")
    CONNECT_WALLET,
    @SerializedName("dana")
    DANA,
    @SerializedName("dci")
    DCI,
    @SerializedName("diners")
    DINERS,
    @SerializedName("discover")
    DISCOVER,
    @SerializedName("eps")
    EPS,
    @SerializedName("gcash")
    GCASH,
    @SerializedName("googlepay")
    GOOGLEPAY,
    @SerializedName("ideal")
    IDEAL,
    @SerializedName("jcb")
    JCB,
    @SerializedName("kakaopay")
    KAKAOPAY,
    @SerializedName("klarna")
    KLARNA,
    @SerializedName("knet")
    KNET,
    @SerializedName("mada")
    MADA,
    @SerializedName("mastercard")
    MASTERCARD,
    @SerializedName("mbway")
    MBWAY,
    @SerializedName("mobilepay")
    MOBILEPAY,
    @SerializedName("multibanco")
    MULTIBANCO,
    @SerializedName("nyce")
    NYCE,
    @SerializedName("octopus")
    OCTOPUS,
    @SerializedName("omannet")
    OMANNET,
    @SerializedName("p24")
    P24,
    @SerializedName("paynow")
    PAYNOW,
    @SerializedName("paypal")
    PAYPAL,
    @SerializedName("paypay")
    PAYPAY,
    @SerializedName("plaid")
    PLAID,
    @SerializedName("pulse")
    PULSE,
    @SerializedName("qpay")
    QPAY,
    @SerializedName("rabbit_line_pay")
    RABBIT_LINE_PAY,
    /**
     * Fully supported by the API but deliberately unlisted in the public specification, so that
     * merchants do not disable Remember Me en masse. Do not remove it as an unspecified value.
     */
    @SerializedName("remember_me")
    REMEMBER_ME,
    @SerializedName("sepa")
    SEPA,
    @SerializedName("sequra")
    SEQURA,
    @SerializedName("shazam")
    SHAZAM,
    @SerializedName("sofort")
    SOFORT,
    @SerializedName("star")
    STAR,
    @SerializedName("stcpay")
    STCPAY,
    @SerializedName("stored_card")
    STORED_CARD,
    @SerializedName("swish")
    SWISH,
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
    @SerializedName("upi")
    UPI,
    @SerializedName("vipps")
    VIPPS,
    @SerializedName("visa")
    VISA,
    @SerializedName("wechatpay")
    WECHATPAY,

    // Payment method categories. These are grouping values used by the flow and payment session
    // entities, not values of the specification's PaymentMethod.type enum.
    @SerializedName("card_scheme")
    CARD_SCHEME,
    @SerializedName("bank_redirects")
    BANK_REDIRECTS,
    @SerializedName("wallet")
    WALLET,
    @SerializedName("bnpl")
    BNPL
}
