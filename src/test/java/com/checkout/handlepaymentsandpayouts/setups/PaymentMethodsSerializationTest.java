package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bizum.Bizum;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodStatus;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal.PayPal;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal.PayPalShippingPreference;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal.PayPalUserAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stcpay.Stcpay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tabby.Tabby;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GSON serialization / deserialization tests for Payment Setup {@link PaymentMethods} DTOs
 * and related payment method types (aligned with swagger {@code CreatePaymentSetup.payment_methods}).
 */
class PaymentMethodsSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeKlarnaWithAction() {
        final String json = "{"
                + "\"status\":\"action_required\","
                + "\"flags\":[\"missing_phone\"],"
                + "\"initialization\":\"enabled\","
                + "\"action\":{"
                + "\"type\":\"sdk\","
                + "\"client_token\":\"ct_klarna_abc\","
                + "\"session_id\":\"sess_111\""
                + "}"
                + "}";

        final Klarna klarna = serializer.fromJson(json, Klarna.class);

        assertNotNull(klarna);
        assertEquals(PaymentMethodStatus.ACTION_REQUIRED, klarna.getStatus());
        assertEquals(Collections.singletonList("missing_phone"), klarna.getFlags());
        assertEquals(PaymentMethodInitialization.ENABLED, klarna.getInitialization());
        assertNotNull(klarna.getAction());
        assertEquals("sdk", klarna.getAction().getType());
        assertEquals("ct_klarna_abc", klarna.getAction().getClientToken());
        assertEquals("sess_111", klarna.getAction().getSessionId());
        assertNull(klarna.getAction().getOrderId());
    }

    @Test
    void shouldRoundTripKlarnaWithAllProperties() {
        final Klarna original = new Klarna();
        original.setStatus(PaymentMethodStatus.READY);
        original.setFlags(Arrays.asList("a", "b"));
        original.setInitialization(PaymentMethodInitialization.ENABLED);
        original.setAction(PaymentMethodAction.builder()
                .type("sdk")
                .clientToken("tok")
                .sessionId("sid")
                .orderId(null)
                .build());

        final String json = serializer.toJson(original);
        final Klarna restored = serializer.fromJson(json, Klarna.class);

        assertNotNull(restored);
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.getFlags(), restored.getFlags());
        assertEquals(original.getInitialization(), restored.getInitialization());
        assertNotNull(restored.getAction());
        assertEquals(original.getAction().getType(), restored.getAction().getType());
        assertEquals(original.getAction().getClientToken(), restored.getAction().getClientToken());
        assertEquals(original.getAction().getSessionId(), restored.getAction().getSessionId());
        assertEquals(original.getAction().getOrderId(), restored.getAction().getOrderId());
    }

    @Test
    void shouldDeserializeStcpayWithOtpAction() {
        final String json = "{"
                + "\"status\":\"pending\","
                + "\"flags\":[\"stc_flag\"],"
                + "\"initialization\":\"enabled\","
                + "\"otp\":\"123456\","
                + "\"action\":{\"type\":\"otp\"}"
                + "}";

        final Stcpay stcpay = serializer.fromJson(json, Stcpay.class);

        assertNotNull(stcpay);
        assertEquals(PaymentMethodStatus.PENDING, stcpay.getStatus());
        assertEquals(Collections.singletonList("stc_flag"), stcpay.getFlags());
        assertEquals(PaymentMethodInitialization.ENABLED, stcpay.getInitialization());
        assertEquals("123456", stcpay.getOtp());
        assertNotNull(stcpay.getAction());
        assertEquals("otp", stcpay.getAction().getType());
        assertNull(stcpay.getAction().getClientToken());
        assertNull(stcpay.getAction().getSessionId());
        assertNull(stcpay.getAction().getOrderId());
    }

    @Test
    void shouldSerializeStcpayOtp() {
        final Stcpay stcpay = new Stcpay();
        stcpay.setStatus(PaymentMethodStatus.AVAILABLE);
        stcpay.setFlags(Collections.emptyList());
        stcpay.setInitialization(PaymentMethodInitialization.DISABLED);
        stcpay.setOtp("654321");

        final String json = serializer.toJson(stcpay);

        assertTrue(json.contains("\"otp\""));
        assertTrue(json.contains("654321"));
    }

    @Test
    void shouldRoundTripStcpayWithAllProperties() {
        final Stcpay original = new Stcpay();
        original.setStatus(PaymentMethodStatus.AVAILABLE);
        original.setFlags(Collections.singletonList("f"));
        original.setInitialization(PaymentMethodInitialization.DISABLED);
        original.setOtp("111222");
        original.setAction(PaymentMethodAction.builder().type("otp").build());

        final String json = serializer.toJson(original);
        final Stcpay restored = serializer.fromJson(json, Stcpay.class);

        assertNotNull(restored);
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.getFlags(), restored.getFlags());
        assertEquals(original.getInitialization(), restored.getInitialization());
        assertEquals(original.getOtp(), restored.getOtp());
        assertNotNull(restored.getAction());
        assertEquals("otp", restored.getAction().getType());
    }

    @Test
    void shouldDeserializeTabbyWithPaymentTypes() {
        final String json = "{"
                + "\"status\":\"ready\","
                + "\"flags\":[],"
                + "\"initialization\":\"enabled\","
                + "\"payment_types\":[\"pay_later\"]"
                + "}";

        final Tabby tabby = serializer.fromJson(json, Tabby.class);

        assertNotNull(tabby);
        assertEquals(PaymentMethodStatus.READY, tabby.getStatus());
        assertEquals(Collections.emptyList(), tabby.getFlags());
        assertEquals(PaymentMethodInitialization.ENABLED, tabby.getInitialization());
        assertEquals(Collections.singletonList("pay_later"), tabby.getPaymentTypes());
    }

    @Test
    void shouldRoundTripTabbyWithAllProperties() {
        final Tabby original = new Tabby();
        original.setStatus(PaymentMethodStatus.UNAVAILABLE);
        original.setFlags(Arrays.asList("t1", "t2"));
        original.setInitialization(PaymentMethodInitialization.DISABLED);
        original.setPaymentTypes(Arrays.asList("pay_later", "installments"));

        final String json = serializer.toJson(original);
        final Tabby restored = serializer.fromJson(json, Tabby.class);

        assertNotNull(restored);
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.getFlags(), restored.getFlags());
        assertEquals(original.getInitialization(), restored.getInitialization());
        assertEquals(original.getPaymentTypes(), restored.getPaymentTypes());
    }

    @Test
    void shouldDeserializePayPalWithAction() {
        final String json = "{"
                + "\"status\":\"available\","
                + "\"flags\":[\"paypal_flag\"],"
                + "\"initialization\":\"enabled\","
                + "\"user_action\":\"pay_now\","
                + "\"brand_name\":\"Test Brand\","
                + "\"shipping_preference\":\"no_shipping\","
                + "\"action\":{"
                + "\"type\":\"sdk\","
                + "\"order_id\":\"ord_paypal_1\""
                + "}"
                + "}";

        final PayPal paypal = serializer.fromJson(json, PayPal.class);

        assertNotNull(paypal);
        assertEquals(PaymentMethodStatus.AVAILABLE, paypal.getStatus());
        assertEquals(Collections.singletonList("paypal_flag"), paypal.getFlags());
        assertEquals(PaymentMethodInitialization.ENABLED, paypal.getInitialization());
        assertEquals(PayPalUserAction.PAY_NOW, paypal.getUserAction());
        assertEquals("Test Brand", paypal.getBrandName());
        assertEquals(PayPalShippingPreference.NO_SHIPPING, paypal.getShippingPreference());
        assertNotNull(paypal.getAction());
        assertEquals("sdk", paypal.getAction().getType());
        assertEquals("ord_paypal_1", paypal.getAction().getOrderId());
        assertNull(paypal.getAction().getClientToken());
        assertNull(paypal.getAction().getSessionId());
    }

    @Test
    void shouldRoundTripPayPalWithAllProperties() {
        final PayPal original = new PayPal();
        original.setStatus(PaymentMethodStatus.PENDING);
        original.setFlags(Collections.singletonList("p"));
        original.setInitialization(PaymentMethodInitialization.ENABLED);
        original.setUserAction(PayPalUserAction.CONTINUE);
        original.setBrandName("Acme");
        original.setShippingPreference(PayPalShippingPreference.GET_FROM_FILE);
        original.setAction(PaymentMethodAction.builder()
                .type("sdk")
                .orderId("oid")
                .clientToken(null)
                .sessionId(null)
                .build());

        final String json = serializer.toJson(original);
        final PayPal restored = serializer.fromJson(json, PayPal.class);

        assertNotNull(restored);
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.getFlags(), restored.getFlags());
        assertEquals(original.getInitialization(), restored.getInitialization());
        assertEquals(original.getUserAction(), restored.getUserAction());
        assertEquals(original.getBrandName(), restored.getBrandName());
        assertEquals(original.getShippingPreference(), restored.getShippingPreference());
        assertNotNull(restored.getAction());
        assertEquals(original.getAction().getType(), restored.getAction().getType());
        assertEquals(original.getAction().getOrderId(), restored.getAction().getOrderId());
    }

    @Test
    void shouldDeserializeBizumWithoutInitialization() {
        final String json = "{"
                + "\"status\":\"unavailable\","
                + "\"flags\":[\"bizum_err\"]"
                + "}";

        final Bizum bizum = serializer.fromJson(json, Bizum.class);

        assertNotNull(bizum);
        assertEquals(PaymentMethodStatus.UNAVAILABLE, bizum.getStatus());
        assertEquals(Collections.singletonList("bizum_err"), bizum.getFlags());
        assertFalse(hasFieldNamed(PaymentMethodBase.class, "initialization"));
        assertFalse(hasFieldNamed(Bizum.class, "initialization"));
    }

    @Test
    void shouldRoundTripBizumWithAllProperties() {
        final Bizum original = new Bizum();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Arrays.asList("x", "y"));

        final String json = serializer.toJson(original);
        final Bizum restored = serializer.fromJson(json, Bizum.class);

        assertNotNull(restored);
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.getFlags(), restored.getFlags());
    }

    @Test
    void shouldSerializePaymentMethodsContainer() {
        final Klarna klarna = new Klarna();
        klarna.setStatus(PaymentMethodStatus.READY);
        klarna.setFlags(Collections.singletonList("k"));
        klarna.setInitialization(PaymentMethodInitialization.ENABLED);

        final PayPal paypal = new PayPal();
        paypal.setStatus(PaymentMethodStatus.AVAILABLE);
        paypal.setFlags(Collections.emptyList());
        paypal.setInitialization(PaymentMethodInitialization.DISABLED);
        paypal.setBrandName("Shop");

        final PaymentMethods methods = PaymentMethods.builder()
                .klarna(klarna)
                .paypal(paypal)
                .build();

        final String json = serializer.toJson(methods);

        assertTrue(json.contains("\"klarna\""));
        assertTrue(json.contains("\"paypal\""));
        assertTrue(json.contains("\"ready\""));
        assertTrue(json.contains("\"available\""));
        assertTrue(json.contains("Shop"));
    }

    @Test
    void shouldRoundTripPaymentMethodsContainer() {
        final PaymentMethods original = PaymentMethods.builder()
                .klarna(buildSampleKlarna())
                .stcpay(buildSampleStcpay())
                .tabby(buildSampleTabby())
                .paypal(buildSamplePayPal())
                .bizum(buildSampleBizum())
                .build();

        final String json = serializer.toJson(original);
        final PaymentMethods restored = serializer.fromJson(json, PaymentMethods.class);

        assertNotNull(restored);
        assertNotNull(restored.getKlarna());
        assertNotNull(restored.getStcpay());
        assertNotNull(restored.getTabby());
        assertNotNull(restored.getPaypal());
        assertNotNull(restored.getBizum());

        assertEquals(original.getKlarna().getStatus(), restored.getKlarna().getStatus());
        assertEquals(original.getKlarna().getFlags(), restored.getKlarna().getFlags());
        assertEquals(original.getKlarna().getInitialization(), restored.getKlarna().getInitialization());
        assertEquals(original.getKlarna().getAction().getType(), restored.getKlarna().getAction().getType());

        assertEquals(original.getStcpay().getOtp(), restored.getStcpay().getOtp());
        assertEquals(original.getTabby().getPaymentTypes(), restored.getTabby().getPaymentTypes());
        assertEquals(original.getPaypal().getBrandName(), restored.getPaypal().getBrandName());
        assertEquals(original.getBizum().getStatus(), restored.getBizum().getStatus());
    }

    @Test
    void shouldDeserializeFullPaymentMethodsFromSwaggerExample() {
        final String json = "{"
                + "\"klarna\":{"
                + "\"status\":\"action_required\","
                + "\"flags\":[\"missing_phone\"],"
                + "\"initialization\":\"enabled\","
                + "\"action\":{"
                + "\"type\":\"sdk\","
                + "\"client_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.ewogICJzZXNzaW9uX2lkIiA6ICIw\","
                + "\"session_id\":\"0b1d9815-165e-42e2-8867-35bc03789e00\""
                + "}"
                + "},"
                + "\"stcpay\":{"
                + "\"status\":\"pending\","
                + "\"flags\":[],"
                + "\"initialization\":\"disabled\","
                + "\"action\":{\"type\":\"otp\"}"
                + "},"
                + "\"tabby\":{"
                + "\"status\":\"ready\","
                + "\"flags\":[],"
                + "\"initialization\":\"enabled\","
                + "\"payment_types\":[\"pay_later\"]"
                + "},"
                + "\"paypal\":{"
                + "\"status\":\"available\","
                + "\"flags\":[],"
                + "\"initialization\":\"enabled\","
                + "\"user_action\":\"pay_now\","
                + "\"brand_name\":\"Acme Corporation\","
                + "\"shipping_preference\":\"no_shipping\","
                + "\"action\":{"
                + "\"type\":\"sdk\","
                + "\"order_id\":\"dd4b1a85-31de-404b-99f4-f143f8ba35ba\""
                + "}"
                + "},"
                + "\"bizum\":{"
                + "\"status\":\"unavailable\","
                + "\"flags\":[\"bizum_setup_incomplete\"]"
                + "}"
                + "}";

        final PaymentMethods methods = serializer.fromJson(json, PaymentMethods.class);

        assertNotNull(methods.getKlarna());
        assertEquals("sdk", methods.getKlarna().getAction().getType());
        assertEquals("0b1d9815-165e-42e2-8867-35bc03789e00", methods.getKlarna().getAction().getSessionId());

        assertNotNull(methods.getStcpay());
        assertEquals("otp", methods.getStcpay().getAction().getType());

        assertNotNull(methods.getTabby());
        assertEquals(Collections.singletonList("pay_later"), methods.getTabby().getPaymentTypes());

        assertNotNull(methods.getPaypal());
        assertEquals(PayPalUserAction.PAY_NOW, methods.getPaypal().getUserAction());
        assertEquals("Acme Corporation", methods.getPaypal().getBrandName());
        assertEquals("dd4b1a85-31de-404b-99f4-f143f8ba35ba", methods.getPaypal().getAction().getOrderId());

        assertNotNull(methods.getBizum());
        assertEquals(PaymentMethodStatus.UNAVAILABLE, methods.getBizum().getStatus());
    }

    @Test
    void shouldSerializePayPalUserAction() {
        final PayPal payNow = new PayPal();
        payNow.setUserAction(PayPalUserAction.PAY_NOW);
        assertTrue(serializer.toJson(payNow).contains("\"pay_now\""));

        final PayPal cont = new PayPal();
        cont.setUserAction(PayPalUserAction.CONTINUE);
        assertTrue(serializer.toJson(cont).contains("\"continue\""));
    }

    @Test
    void shouldSerializePayPalShippingPreference() {
        final PayPal noShipping = new PayPal();
        noShipping.setShippingPreference(PayPalShippingPreference.NO_SHIPPING);
        assertTrue(serializer.toJson(noShipping).contains("\"no_shipping\""));

        final PayPal fromFile = new PayPal();
        fromFile.setShippingPreference(PayPalShippingPreference.GET_FROM_FILE);
        assertTrue(serializer.toJson(fromFile).contains("\"get_from_file\""));

        final PayPal provided = new PayPal();
        provided.setShippingPreference(PayPalShippingPreference.SET_PROVIDED_ADDRESS);
        assertTrue(serializer.toJson(provided).contains("\"set_provided_address\""));
    }

    @Test
    void shouldRoundTripPaymentMethodActionWithAllProperties() {
        final PaymentMethodAction original = PaymentMethodAction.builder()
                .type("sdk")
                .clientToken("ct_all")
                .sessionId("sid_all")
                .orderId("ord_all")
                .build();

        final String json = serializer.toJson(original);
        final PaymentMethodAction restored = serializer.fromJson(json, PaymentMethodAction.class);

        assertNotNull(restored);
        assertEquals(original.getType(), restored.getType());
        assertEquals(original.getClientToken(), restored.getClientToken());
        assertEquals(original.getSessionId(), restored.getSessionId());
        assertEquals(original.getOrderId(), restored.getOrderId());
        assertTrue(json.contains("\"client_token\""));
        assertTrue(json.contains("\"session_id\""));
        assertTrue(json.contains("\"order_id\""));
    }

    @Test
    void shouldDeserializeAllPaymentMethodStatusWireValues() {
        assertEquals(PaymentMethodStatus.UNAVAILABLE, serializer.fromJson("{\"status\":\"unavailable\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.ACTION_REQUIRED, serializer.fromJson("{\"status\":\"action_required\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.PENDING, serializer.fromJson("{\"status\":\"pending\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.READY, serializer.fromJson("{\"status\":\"ready\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.AVAILABLE, serializer.fromJson("{\"status\":\"available\"}", Klarna.class).getStatus());
    }

    @Test
    void shouldSerializeAllPaymentMethodStatusValues() {
        for (final PaymentMethodStatus status : PaymentMethodStatus.values()) {
            final Klarna k = new Klarna();
            k.setStatus(status);
            final String json = serializer.toJson(k);
            switch (status) {
                case UNAVAILABLE:
                    assertTrue(json.contains("\"unavailable\""));
                    break;
                case ACTION_REQUIRED:
                    assertTrue(json.contains("\"action_required\""));
                    break;
                case PENDING:
                    assertTrue(json.contains("\"pending\""));
                    break;
                case READY:
                    assertTrue(json.contains("\"ready\""));
                    break;
                case AVAILABLE:
                    assertTrue(json.contains("\"available\""));
                    break;
                default:
                    throw new IllegalStateException("Unhandled status: " + status);
            }
        }
    }

    @Test
    void shouldRoundTripPaymentMethodInitialization() {
        for (final PaymentMethodInitialization init : PaymentMethodInitialization.values()) {
            final Klarna k = new Klarna();
            k.setInitialization(init);
            final Klarna restored = serializer.fromJson(serializer.toJson(k), Klarna.class);
            assertEquals(init, restored.getInitialization());
        }
    }

    private static Klarna buildSampleKlarna() {
        final Klarna k = new Klarna();
        k.setStatus(PaymentMethodStatus.READY);
        k.setFlags(Collections.singletonList("kf"));
        k.setInitialization(PaymentMethodInitialization.ENABLED);
        k.setAction(PaymentMethodAction.builder().type("sdk").clientToken("c").sessionId("s").build());
        return k;
    }

    private static Stcpay buildSampleStcpay() {
        final Stcpay s = new Stcpay();
        s.setStatus(PaymentMethodStatus.PENDING);
        s.setFlags(Collections.emptyList());
        s.setInitialization(PaymentMethodInitialization.DISABLED);
        s.setOtp("999888");
        s.setAction(PaymentMethodAction.builder().type("otp").build());
        return s;
    }

    private static Tabby buildSampleTabby() {
        final Tabby t = new Tabby();
        t.setStatus(PaymentMethodStatus.AVAILABLE);
        t.setFlags(Collections.singletonList("tf"));
        t.setInitialization(PaymentMethodInitialization.ENABLED);
        t.setPaymentTypes(Collections.singletonList("pay_later"));
        return t;
    }

    private static PayPal buildSamplePayPal() {
        final PayPal p = new PayPal();
        p.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        p.setFlags(Collections.singletonList("pf"));
        p.setInitialization(PaymentMethodInitialization.DISABLED);
        p.setUserAction(PayPalUserAction.PAY_NOW);
        p.setBrandName("Brand");
        p.setShippingPreference(PayPalShippingPreference.SET_PROVIDED_ADDRESS);
        p.setAction(PaymentMethodAction.builder().type("sdk").orderId("o1").build());
        return p;
    }

    private static Bizum buildSampleBizum() {
        final Bizum b = new Bizum();
        b.setStatus(PaymentMethodStatus.UNAVAILABLE);
        b.setFlags(Arrays.asList("b1", "b2"));
        return b;
    }

    private static boolean hasFieldNamed(final Class<?> clazz, final String name) {
        for (final Field f : clazz.getDeclaredFields()) {
            if (name.equals(f.getName())) {
                return true;
            }
        }
        return false;
    }
}
