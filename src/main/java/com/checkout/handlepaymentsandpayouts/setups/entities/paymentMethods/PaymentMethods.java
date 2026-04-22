package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach.Ach;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alipay.AlipayCn;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alipay.AlipayHk;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alma.Alma;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.applepay.ApplePay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bancontact.Bancontact;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.benefit.Benefit;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bizum.Bizum;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card.Card;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment methods configuration for a payment setup.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentMethods {

    /**
     * Instrument (saved payment source) configuration.
     * [Optional]
     */
    private Instrument instrument;

    /**
     * Klarna payment method configuration.
     * [Optional]
     */
    private Klarna klarna;

    /**
     * STC Pay payment method configuration.
     * [Optional]
     */
    private Stcpay stcpay;

    /**
     * Tabby payment method configuration.
     * [Optional]
     */
    private Tabby tabby;

    /**
     * PayPal payment method configuration.
     * [Optional]
     */
    private PayPal paypal;

    /**
     * Bizum payment method configuration.
     * [Optional]
     */
    private Bizum bizum;

    /**
     * PayNow payment method configuration.
     * [Optional]
     */
    private PayNow paynow;

    /**
     * QPay payment method configuration.
     * [Optional]
     */
    private Qpay qpay;

    /**
     * EPS payment method configuration.
     * [Optional]
     */
    private Eps eps;

    /**
     * iDEAL payment method configuration.
     * [Optional]
     */
    private Ideal ideal;

    /**
     * KNET payment method configuration.
     * [Optional]
     */
    private Knet knet;

    /**
     * Bancontact payment method configuration.
     * [Optional]
     */
    private Bancontact bancontact;

    /**
     * Benefit payment method configuration.
     * [Optional]
     */
    private Benefit benefit;

    /**
     * Vipps payment method configuration.
     * [Optional]
     */
    private Vipps vipps;

    /**
     * Twint payment method configuration.
     * [Optional]
     */
    private Twint twint;

    /**
     * Alipay CN payment method configuration.
     * [Optional]
     */
    private AlipayCn alipayCn;

    /**
     * Alipay HK payment method configuration.
     * [Optional]
     */
    private AlipayHk alipayHk;

    /**
     * GCash payment method configuration.
     * [Optional]
     */
    private GCash gcash;

    /**
     * TNG payment method configuration.
     * [Optional]
     */
    private Tng tng;

    /**
     * Dana payment method configuration.
     * [Optional]
     */
    private Dana dana;

    /**
     * MobilePay payment method configuration.
     * [Optional]
     */
    private MobilePay mobilepay;

    /**
     * Tamara payment method configuration.
     * [Optional]
     */
    private Tamara tamara;

    /**
     * MBWay payment method configuration.
     * [Optional]
     */
    private MBWay mbway;

    /**
     * Multibanco payment method configuration.
     * [Optional]
     */
    private Multibanco multibanco;

    /**
     * WeChatPay payment method configuration.
     * [Optional]
     */
    private WeChatPay wechatpay;

    /**
     * KakaoPay payment method configuration.
     * [Optional]
     */
    private KakaoPay kakaopay;

    /**
     * TrueMoney payment method configuration.
     * [Optional]
     */
    private TrueMoney truemoney;

    /**
     * Octopus payment method configuration.
     * [Optional]
     */
    private Octopus octopus;

    /**
     * P24 (Przelewy24) payment method configuration.
     * [Optional]
     */
    private P24 p24;

    /**
     * Alma payment method configuration.
     * [Optional]
     */
    private Alma alma;

    /**
     * Swish payment method configuration.
     * [Optional]
     */
    private Swish swish;

    /**
     * Sequra payment method configuration.
     * [Optional]
     */
    private Sequra sequra;

    /**
     * ACH (Direct Debit) payment method configuration.
     * [Optional]
     */
    private Ach ach;

    /**
     * SEPA (Direct Debit) payment method configuration.
     * [Optional]
     */
    private Sepa sepa;

    /**
     * GooglePay payment method configuration.
     * [Optional]
     */
    private GooglePay googlepay;

    /**
     * ApplePay payment method configuration.
     * [Optional]
     */
    private ApplePay applepay;

    /**
     * Card payment method configuration.
     * [Optional]
     */
    private Card card;
}
