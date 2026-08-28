package com.checkout.common;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Value-by-value serialization test for {@link PaymentMethodType}.
 *
 * <p>This enum is a consolidated union: it carries every payment method type the SDK sees across
 * GET /payment-methods, the flow API and the payment session entities. The specification's
 * PaymentMethod.type enum is therefore a subset of it, and the values this enum adds on top
 * (applepay, card, googlepay, stored_card, wallet, bnpl, bank_redirects, and the APM values the
 * flow API returns) are deliberate rather than invented. remember_me is supported by the API but
 * deliberately unlisted in the public specification.
 *
 * <p>The full map below is the guard: GET /payment-methods maps a value it does not recognise to
 * null rather than failing, so a missing constant is silent. bacs went missing that way.
 */
class PaymentMethodTypeSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeEveryValueBothDirections() {
        wireValues().forEach((value, wire) -> {
            assertEquals("\"" + wire + "\"", serializer.toJson(value), value.name());
            assertEquals(value, serializer.fromJson("\"" + wire + "\"", PaymentMethodType.class), wire);
        });
    }

    @Test
    void shouldSerializeBacsBothDirections() {
        assertEquals("\"bacs\"", serializer.toJson(PaymentMethodType.BACS));
        assertEquals(PaymentMethodType.BACS, serializer.fromJson("\"bacs\"", PaymentMethodType.class));
    }

    private Map<PaymentMethodType, String> wireValues() {
        final Map<PaymentMethodType, String> expected = new LinkedHashMap<>();
        expected.put(PaymentMethodType.ACCEL, "accel");
        expected.put(PaymentMethodType.ACH, "ach");
        expected.put(PaymentMethodType.ALIPAY_CN, "alipay_cn");
        expected.put(PaymentMethodType.ALIPAY_HK, "alipay_hk");
        expected.put(PaymentMethodType.ALIPAY_PLUS, "alipay_plus");
        expected.put(PaymentMethodType.ALMA, "alma");
        expected.put(PaymentMethodType.AMEX, "amex");
        expected.put(PaymentMethodType.APPLEPAY, "applepay");
        expected.put(PaymentMethodType.BACS, "bacs");
        expected.put(PaymentMethodType.BANCONTACT, "bancontact");
        expected.put(PaymentMethodType.BANK_REDIRECTS, "bank_redirects");
        expected.put(PaymentMethodType.BENEFIT, "benefit");
        expected.put(PaymentMethodType.BIZUM, "bizum");
        expected.put(PaymentMethodType.BLIK, "blik");
        expected.put(PaymentMethodType.BNPL, "bnpl");
        expected.put(PaymentMethodType.BOOST, "boost");
        expected.put(PaymentMethodType.BPI, "bpi");
        expected.put(PaymentMethodType.CARD, "card");
        expected.put(PaymentMethodType.CARD_SCHEME, "card_scheme");
        expected.put(PaymentMethodType.CARTES_BANCAIRES, "cartes_bancaires");
        expected.put(PaymentMethodType.CHINA_UNION_PAY, "china_union_pay");
        expected.put(PaymentMethodType.CONNECT_WALLET, "connect_wallet");
        expected.put(PaymentMethodType.DANA, "dana");
        expected.put(PaymentMethodType.DCI, "dci");
        expected.put(PaymentMethodType.DINERS, "diners");
        expected.put(PaymentMethodType.DISCOVER, "discover");
        expected.put(PaymentMethodType.EPS, "eps");
        expected.put(PaymentMethodType.GCASH, "gcash");
        expected.put(PaymentMethodType.GOOGLEPAY, "googlepay");
        expected.put(PaymentMethodType.IDEAL, "ideal");
        expected.put(PaymentMethodType.JCB, "jcb");
        expected.put(PaymentMethodType.KAKAOPAY, "kakaopay");
        expected.put(PaymentMethodType.KLARNA, "klarna");
        expected.put(PaymentMethodType.KNET, "knet");
        expected.put(PaymentMethodType.MADA, "mada");
        expected.put(PaymentMethodType.MASTERCARD, "mastercard");
        expected.put(PaymentMethodType.MBWAY, "mbway");
        expected.put(PaymentMethodType.MOBILEPAY, "mobilepay");
        expected.put(PaymentMethodType.MULTIBANCO, "multibanco");
        expected.put(PaymentMethodType.NYCE, "nyce");
        expected.put(PaymentMethodType.OCTOPUS, "octopus");
        expected.put(PaymentMethodType.OMANNET, "omannet");
        expected.put(PaymentMethodType.P24, "p24");
        expected.put(PaymentMethodType.PAYNOW, "paynow");
        expected.put(PaymentMethodType.PAYPAL, "paypal");
        expected.put(PaymentMethodType.PAYPAY, "paypay");
        expected.put(PaymentMethodType.PLAID, "plaid");
        expected.put(PaymentMethodType.PULSE, "pulse");
        expected.put(PaymentMethodType.QPAY, "qpay");
        expected.put(PaymentMethodType.RABBIT_LINE_PAY, "rabbit_line_pay");
        expected.put(PaymentMethodType.REMEMBER_ME, "remember_me");
        expected.put(PaymentMethodType.SEPA, "sepa");
        expected.put(PaymentMethodType.SEQURA, "sequra");
        expected.put(PaymentMethodType.SHAZAM, "shazam");
        expected.put(PaymentMethodType.SOFORT, "sofort");
        expected.put(PaymentMethodType.STAR, "star");
        expected.put(PaymentMethodType.STCPAY, "stcpay");
        expected.put(PaymentMethodType.STORED_CARD, "stored_card");
        expected.put(PaymentMethodType.SWISH, "swish");
        expected.put(PaymentMethodType.TABBY, "tabby");
        expected.put(PaymentMethodType.TAMARA, "tamara");
        expected.put(PaymentMethodType.TNG, "tng");
        expected.put(PaymentMethodType.TRUEMONEY, "truemoney");
        expected.put(PaymentMethodType.TWINT, "twint");
        expected.put(PaymentMethodType.UPI, "upi");
        expected.put(PaymentMethodType.VIPPS, "vipps");
        expected.put(PaymentMethodType.VISA, "visa");
        expected.put(PaymentMethodType.WALLET, "wallet");
        expected.put(PaymentMethodType.WECHATPAY, "wechatpay");

        assertEquals(expected.size(), PaymentMethodType.values().length,
                "every constant must be listed so the enum cannot grow without this test noticing");
        return expected;
    }
}
