package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach.Ach;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alipay.AlipayCn;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alipay.AlipayHk;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alma.Alma;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.applepay.ApplePay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.Bacs;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bancontact.Bancontact;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.benefit.Benefit;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bizum.Bizum;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.blik.Blik;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card.Card;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cardpresent.CardPresent;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp.CashApp;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank.PayByBank;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stablecoin.Stablecoin;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.dana.Dana;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.eps.Eps;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.gcash.GCash;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.googlepay.GooglePay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ideal.Ideal;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.instrument.Instrument;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.kakaopay.KakaoPay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.knet.Knet;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.mbway.MBWay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.mobilepay.MobilePay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.multibanco.Multibanco;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.octopus.Octopus;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.p24.P24;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paynow.PayNow;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal.PayPal;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.qpay.Qpay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa.Sepa;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sequra.Sequra;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stcpay.Stcpay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.swish.Swish;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tabby.Tabby;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tamara.Tamara;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tng.Tng;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.truemoney.TrueMoney;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.twint.Twint;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.vipps.Vipps;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.wechatpay.WeChatPay;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The payment methods that are enabled on your account and available for use.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentMethods {

    /**
     * The payment method that automatically confirms the payment when it is ready.
     * For example, card.
     * [Optional]
     */
    private String autoConfirm;

    /**
     * The instrument payment method's details and configuration.
     * [Optional]
     */
    private Instrument instrument;

    /**
     * The Klarna payment method's details and configuration.
     * [Optional]
     */
    private Klarna klarna;

    /**
     * The stc pay payment method's details and configuration.
     * [Optional]
     */
    private Stcpay stcpay;

    /**
     * The Tabby payment method's details and configuration.
     * [Optional]
     */
    private Tabby tabby;

    /**
     * The PayPal payment method's details and configuration.
     * [Optional]
     */
    private PayPal paypal;

    /**
     * The Bizum payment method's details and configuration.
     * [Optional] readOnly
     */
    private Bizum bizum;

    /**
     * The Blik payment method's details and configuration.
     * [Optional]
     */
    private Blik blik;

    /**
     * The PayNow payment method's details and configuration.
     * [Optional] readOnly
     */
    private PayNow paynow;

    /**
     * The QPay payment method's details and configuration.
     * [Optional]
     */
    private Qpay qpay;

    /**
     * The EPS payment method's details and configuration.
     * [Optional] readOnly
     */
    private Eps eps;

    /**
     * The iDEAL payment method's details and configuration.
     * [Optional]
     */
    private Ideal ideal;

    /**
     * The KNET payment method's details and configuration.
     * [Optional]
     */
    private Knet knet;

    /**
     * The Bancontact payment method's details and configuration.
     * [Optional]
     */
    private Bancontact bancontact;

    /**
     * The Benefit payment method's details and configuration.
     * [Optional] readOnly
     */
    private Benefit benefit;

    /**
     * The Vipps payment method's details and configuration.
     * [Optional] readOnly
     */
    private Vipps vipps;

    /**
     * The Twint payment method's details and configuration.
     * [Optional] readOnly
     */
    private Twint twint;

    /**
     * The Alipay CN payment method's details and configuration.
     * [Optional]
     */
    private AlipayCn alipayCn;

    /**
     * The Alipay HK payment method's details and configuration.
     * [Optional]
     */
    private AlipayHk alipayHk;

    /**
     * The GCash payment method's details and configuration.
     * [Optional]
     */
    private GCash gcash;

    /**
     * The TNG payment method's details and configuration.
     * [Optional]
     */
    private Tng tng;

    /**
     * The Dana payment method's details and configuration.
     * [Optional]
     */
    private Dana dana;

    /**
     * The MobilePay payment method's details and configuration.
     * [Optional] readOnly
     */
    private MobilePay mobilepay;

    /**
     * The Tamara payment method's details and configuration.
     * [Optional]
     */
    private Tamara tamara;

    /**
     * The MBWay payment method's details and configuration.
     * [Optional] readOnly
     */
    private MBWay mbway;

    /**
     * The Multibanco payment method's details and configuration.
     * [Optional]
     */
    private Multibanco multibanco;

    /**
     * The WeChatPay payment method's details and configuration.
     * [Optional] readOnly
     */
    private WeChatPay wechatpay;

    /**
     * The KakaoPay payment method's details and configuration.
     * [Optional]
     */
    private KakaoPay kakaopay;

    /**
     * The TrueMoney payment method's details and configuration.
     * [Optional]
     */
    private TrueMoney truemoney;

    /**
     * The Octopus payment method's details and configuration.
     * [Optional] readOnly
     */
    private Octopus octopus;

    /**
     * The P24 (Przelewy24) payment method's details and configuration.
     * [Optional]
     */
    private P24 p24;

    /**
     * The Alma payment method's details and configuration.
     * [Optional]
     */
    private Alma alma;

    /**
     * The Swish payment method's details and configuration.
     * [Optional]
     */
    private Swish swish;

    /**
     * The Sequra payment method's details and configuration.
     * [Optional]
     */
    private Sequra sequra;

    /**
     * The ACH payment method's details and configuration.
     * [Optional]
     */
    private Ach ach;

    /**
     * The SEPA payment method's details and configuration.
     * [Optional]
     */
    private Sepa sepa;

    /**
     * The Google Pay payment method's details and configuration.
     * [Optional]
     */
    private GooglePay googlepay;

    /**
     * The Apple Pay payment method's details and configuration.
     * [Optional]
     */
    private ApplePay applepay;

    /**
     * The Card payment method's details and configuration.
     * [Optional]
     */
    private Card card;

    /**
     * The Bacs payment method's details and configuration.
     * [Optional]
     */
    private Bacs bacs;

    /**
     * The Card Present payment method's details and configuration.
     * [Optional]
     *
     * @deprecated card_present is no longer part of the Payment Setups API specification. There is no
     * replacement. Will be removed in the next major version.
     */
    @Deprecated
    private CardPresent cardPresent;

    /**
     * The Pay by Bank (Open Banking) payment method's details and configuration.
     * Serialized as {@code paybybank}.
     * [Optional]
     */
    @SerializedName("paybybank")
    private PayByBank payByBank;

    /**
     * The Stablecoin payment method's details and configuration.
     * [Optional]
     */
    private Stablecoin stablecoin;

    /**
     * The Cash App payment method's details and configuration.
     * The wire key is the single word cashapp, so keep this field name lowercase: the naming
     * policy turns cashApp into cash_app.
     * [Optional]
     */
    private CashApp cashapp;
}
