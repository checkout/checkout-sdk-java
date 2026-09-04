package com.checkout.common;

import com.google.gson.annotations.SerializedName;

/**
 * The payment source type.
 *
 * <p>This enum is the union of the source types accepted on payment requests and returned on
 * payment responses, on both the current and the previous platform, so it also carries
 * previous-platform values that the current API specification no longer declares.
 */
public enum PaymentSourceType {

    @SerializedName("ach")
    ACH,
    @SerializedName("afterpay")
    AFTERPAY,
    @SerializedName("alipay")
    ALIPAY,
    @SerializedName("alipay_cn")
    ALIPAY_CN,
    @SerializedName("alipay_hk")
    ALIPAY_HK,
    @SerializedName("alipay_plus")
    ALIPAY_PLUS,
    @SerializedName("alma")
    ALMA,
    @SerializedName("applepay")
    APPLEPAY,
    @SerializedName("bacs")
    BACS,
    @SerializedName("bancontact")
    BANCONTACT,
    @SerializedName("bank_account")
    BANK_ACCOUNT,
    @SerializedName("benefit")
    BENEFIT,
    @SerializedName("benefitpay")
    BENEFITPAY,
    @SerializedName("bizum")
    BIZUM,
    @SerializedName("blik")
    BLIK,
    @SerializedName("boleto")
    BOLETO,
    @SerializedName("card")
    CARD,
    @SerializedName("currency_account")
    CURRENCY_ACCOUNT,
    @SerializedName("customer")
    CUSTOMER,
    @SerializedName("cvconnect")
    CV_CONNECT,
    @SerializedName("dana")
    DANA,
    @SerializedName("dlocal")
    DLOCAL,
    @SerializedName("eps")
    EPS,
    @SerializedName("fawry")
    FAWRY,
    @SerializedName("gcash")
    GCASH,
    @SerializedName("giropay")
    GIROPAY,
    @SerializedName("googlepay")
    GOOGLEPAY,
    @SerializedName("id")
    ID,
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
    @SerializedName("network_token")
    NETWORK_TOKEN,
    @SerializedName("octopus")
    OCTOPUS,
    @SerializedName("oxxo")
    OXXO,
    @SerializedName("p24")
    P24,
    @SerializedName("pagofacil")
    PAGOFACIL,
    @SerializedName("paypal")
    PAYPAL,
    @SerializedName("plaid")
    PLAID,
    @SerializedName("poli")
    POLI,
    @SerializedName("postfinance")
    POSTFINANCE,
    @SerializedName("provider_token")
    PROVIDER_TOKEN,
    @SerializedName("qpay")
    QPAY,
    @SerializedName("rapipago")
    RAPIPAGO,
    /**
     * Shares the wire value of {@link #ID}, because the previous platform references a stored SEPA
     * mandate through the generic "id" source. Nothing in the SDK passes this constant any more:
     * the previous-platform source now uses {@link #ID} directly, which is how the .NET SDK models
     * the same call.
     *
     * <p>Gson resolves an incoming "id" to whichever of the two constants is declared last, which
     * is this one, so deserializing a previous-platform source yields SEPA rather than ID. Removing
     * this constant would correct that but is a breaking change, so it is deprecated instead. Do
     * not reorder the two constants: that would silently change what "id" deserializes to.
     *
     * @deprecated use {@link #ID} for the previous platform and {@link #SEPAV4} for the current
     * platform, where the wire value is "sepa".
     */
    @Deprecated
    @SerializedName("id")
    SEPA,
    @SerializedName("sepa")
    SEPAV4,
    @SerializedName("sequra")
    SEQURA,
    @SerializedName("sofort")
    SOFORT,
    @SerializedName("stcpay")
    STCPAY,
    @SerializedName("swish")
    SWISH,
    @SerializedName("tabby")
    TABBY,
    @SerializedName("tamara")
    TAMARA,
    @SerializedName("token")
    TOKEN,
    @SerializedName("tng")
    TNG,
    @SerializedName("truemoney")
    TRUEMONEY,
    @SerializedName("trustly")
    TRUSTLY,
    @SerializedName("twint")
    TWINT,
    @SerializedName("vipps")
    VIPPS,
    @SerializedName("wechatpay")
    WECHATPAY,
    @SerializedName("paynow")
    PAYNOW;
}
