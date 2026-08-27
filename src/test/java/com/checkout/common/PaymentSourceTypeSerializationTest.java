package com.checkout.common;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Value-by-value serialization test for {@link PaymentSourceType}.
 *
 * <p>The deprecated SEPA constant is asserted deliberately: it stays on the enum for backwards
 * compatibility, so its wire value still has to be pinned.
 */
@SuppressWarnings("deprecation")
class PaymentSourceTypeSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    /**
     * SEPA and ID deliberately share the wire value "id", because the previous platform references a
     * stored SEPA mandate through the generic "id" source. Gson resolves an incoming "id" to the
     * constant declared last, so only the serialize direction is asserted for those two.
     */
    private static final Map<PaymentSourceType, String> AMBIGUOUS_ON_READ = new LinkedHashMap<>();

    static {
        AMBIGUOUS_ON_READ.put(PaymentSourceType.SEPA, "id");
        AMBIGUOUS_ON_READ.put(PaymentSourceType.ID, "id");
    }

    @Test
    void shouldSerializeEveryValueToItsWireString() {
        wireValues().forEach((value, wire) ->
                assertEquals("\"" + wire + "\"", serializer.toJson(value), value.name()));
    }

    @Test
    void shouldDeserializeEveryUnambiguousWireStringBackToItsValue() {
        wireValues().forEach((value, wire) -> {
            if (AMBIGUOUS_ON_READ.containsKey(value)) {
                return;
            }
            assertEquals(value, serializer.fromJson("\"" + wire + "\"", PaymentSourceType.class), wire);
        });
    }

    @Test
    void shouldResolveTheSharedIdWireValueToTheLastDeclaredConstant() {
        // SEPA is declared after ID, so Gson resolves "id" to SEPA. Reordering the two constants
        // would silently change this, which is why the enum documents the ordering as load-bearing.
        assertEquals(PaymentSourceType.SEPA, serializer.fromJson("\"id\"", PaymentSourceType.class));
        assertEquals("\"id\"", serializer.toJson(PaymentSourceType.ID));
        assertEquals("\"id\"", serializer.toJson(PaymentSourceType.SEPA));
    }

    @Test
    void shouldSerializeBacsBothDirections() {
        assertEquals("\"bacs\"", serializer.toJson(PaymentSourceType.BACS));
        assertEquals(PaymentSourceType.BACS, serializer.fromJson("\"bacs\"", PaymentSourceType.class));
    }

    @Test
    void shouldSerializeCurrentPlatformSepaAsSepa() {
        assertEquals("\"sepa\"", serializer.toJson(PaymentSourceType.SEPAV4));
        assertEquals(PaymentSourceType.SEPAV4, serializer.fromJson("\"sepa\"", PaymentSourceType.class));
    }

    private Map<PaymentSourceType, String> wireValues() {
        final Map<PaymentSourceType, String> expected = new LinkedHashMap<>();
        expected.put(PaymentSourceType.ACH, "ach");
        expected.put(PaymentSourceType.AFTERPAY, "afterpay");
        expected.put(PaymentSourceType.ALIPAY, "alipay");
        expected.put(PaymentSourceType.ALIPAY_CN, "alipay_cn");
        expected.put(PaymentSourceType.ALIPAY_HK, "alipay_hk");
        expected.put(PaymentSourceType.ALIPAY_PLUS, "alipay_plus");
        expected.put(PaymentSourceType.ALMA, "alma");
        expected.put(PaymentSourceType.APPLEPAY, "applepay");
        expected.put(PaymentSourceType.BACS, "bacs");
        expected.put(PaymentSourceType.BANCONTACT, "bancontact");
        expected.put(PaymentSourceType.BANK_ACCOUNT, "bank_account");
        expected.put(PaymentSourceType.BENEFIT, "benefit");
        expected.put(PaymentSourceType.BENEFITPAY, "benefitpay");
        expected.put(PaymentSourceType.BIZUM, "bizum");
        expected.put(PaymentSourceType.BLIK, "blik");
        expected.put(PaymentSourceType.BOLETO, "boleto");
        expected.put(PaymentSourceType.CARD, "card");
        expected.put(PaymentSourceType.CURRENCY_ACCOUNT, "currency_account");
        expected.put(PaymentSourceType.CUSTOMER, "customer");
        expected.put(PaymentSourceType.CV_CONNECT, "cvconnect");
        expected.put(PaymentSourceType.DANA, "dana");
        expected.put(PaymentSourceType.DLOCAL, "dlocal");
        expected.put(PaymentSourceType.EPS, "eps");
        expected.put(PaymentSourceType.FAWRY, "fawry");
        expected.put(PaymentSourceType.GCASH, "gcash");
        expected.put(PaymentSourceType.GIROPAY, "giropay");
        expected.put(PaymentSourceType.GOOGLEPAY, "googlepay");
        expected.put(PaymentSourceType.ID, "id");
        expected.put(PaymentSourceType.IDEAL, "ideal");
        expected.put(PaymentSourceType.ILLICADO, "illicado");
        expected.put(PaymentSourceType.KAKAOPAY, "kakaopay");
        expected.put(PaymentSourceType.KLARNA, "klarna");
        expected.put(PaymentSourceType.KNET, "knet");
        expected.put(PaymentSourceType.MBWAY, "mbway");
        expected.put(PaymentSourceType.MOBILEPAY, "mobilepay");
        expected.put(PaymentSourceType.MULTIBANCO, "multibanco");
        expected.put(PaymentSourceType.NETWORK_TOKEN, "network_token");
        expected.put(PaymentSourceType.OCTOPUS, "octopus");
        expected.put(PaymentSourceType.OXXO, "oxxo");
        expected.put(PaymentSourceType.P24, "p24");
        expected.put(PaymentSourceType.PAGOFACIL, "pagofacil");
        expected.put(PaymentSourceType.PAYPAL, "paypal");
        expected.put(PaymentSourceType.PLAID, "plaid");
        expected.put(PaymentSourceType.POLI, "poli");
        expected.put(PaymentSourceType.POSTFINANCE, "postfinance");
        expected.put(PaymentSourceType.PROVIDER_TOKEN, "provider_token");
        expected.put(PaymentSourceType.QPAY, "qpay");
        expected.put(PaymentSourceType.RAPIPAGO, "rapipago");
        expected.put(PaymentSourceType.SEPA, "id");
        expected.put(PaymentSourceType.SEPAV4, "sepa");
        expected.put(PaymentSourceType.SEQURA, "sequra");
        expected.put(PaymentSourceType.SOFORT, "sofort");
        expected.put(PaymentSourceType.STCPAY, "stcpay");
        expected.put(PaymentSourceType.SWISH, "swish");
        expected.put(PaymentSourceType.TABBY, "tabby");
        expected.put(PaymentSourceType.TAMARA, "tamara");
        expected.put(PaymentSourceType.TOKEN, "token");
        expected.put(PaymentSourceType.TNG, "tng");
        expected.put(PaymentSourceType.TRUEMONEY, "truemoney");
        expected.put(PaymentSourceType.TRUSTLY, "trustly");
        expected.put(PaymentSourceType.TWINT, "twint");
        expected.put(PaymentSourceType.VIPPS, "vipps");
        expected.put(PaymentSourceType.WECHATPAY, "wechatpay");
        expected.put(PaymentSourceType.PAYNOW, "paynow");

        assertEquals(expected.size(), PaymentSourceType.values().length,
                "every constant must be listed so the enum cannot grow without this test noticing");
        return expected;
    }
}
