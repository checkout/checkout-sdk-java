package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bizum.Bizum;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card.Card;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card.CardDetails;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card.CardToken;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodStatus;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentSetupScheme;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.instrument.Instrument;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal.PayPal;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal.PayPalShippingPreference;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal.PayPalUserAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stcpay.Stcpay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tabby.Tabby;
import com.checkout.payments.PreferredSchema;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
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
                + "\"status\":\"action_required\","
                + "\"flags\":[\"stc_flag\"],"
                + "\"initialization\":\"enabled\","
                + "\"otp\":\"123456\","
                + "\"action\":{\"type\":\"otp\"}"
                + "}";

        final Stcpay stcpay = serializer.fromJson(json, Stcpay.class);

        assertNotNull(stcpay);
        assertEquals(PaymentMethodStatus.ACTION_REQUIRED, stcpay.getStatus());
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
        stcpay.setStatus(PaymentMethodStatus.READY);
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
        original.setStatus(PaymentMethodStatus.INITIALIZATION_REQUIRED);
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
                + "\"status\":\"ready\","
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
        assertEquals(PaymentMethodStatus.READY, paypal.getStatus());
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
        original.setStatus(PaymentMethodStatus.INVALID);
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
        paypal.setStatus(PaymentMethodStatus.READY);
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
                + "\"status\":\"action_required\","
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
                + "\"status\":\"ready\","
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

    /**
     * PENDING and AVAILABLE are deprecated (not in the PaymentMethodStatus enum of the spec) but still
     * deserialize, so a response carrying them does not break existing integrations.
     */
    @Test
    @SuppressWarnings("deprecation")
    void shouldDeserializeAllPaymentMethodStatusWireValues() {
        assertEquals(PaymentMethodStatus.UNAVAILABLE, serializer.fromJson("{\"status\":\"unavailable\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.ACTION_REQUIRED, serializer.fromJson("{\"status\":\"action_required\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.PENDING, serializer.fromJson("{\"status\":\"pending\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.READY, serializer.fromJson("{\"status\":\"ready\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.AVAILABLE, serializer.fromJson("{\"status\":\"available\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.INITIALIZATION_REQUIRED, serializer.fromJson("{\"status\":\"initialization_required\"}", Klarna.class).getStatus());
        assertEquals(PaymentMethodStatus.INVALID, serializer.fromJson("{\"status\":\"invalid\"}", Klarna.class).getStatus());
    }

    @Test
    @SuppressWarnings("deprecation")
    void shouldMarkOnlyTheStatusValuesOutsideTheSpecAsDeprecated() throws NoSuchFieldException {
        for (final PaymentMethodStatus status : PaymentMethodStatus.values()) {
            final boolean deprecated = PaymentMethodStatus.class.getField(status.name()).isAnnotationPresent(Deprecated.class);
            final boolean inSpec = status != PaymentMethodStatus.PENDING && status != PaymentMethodStatus.AVAILABLE;
            assertEquals(!inSpec, deprecated, status.name());
        }
    }

    @Test
    @SuppressWarnings("deprecation")
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
                case INITIALIZATION_REQUIRED:
                    assertTrue(json.contains("\"initialization_required\""));
                    break;
                case INVALID:
                    assertTrue(json.contains("\"invalid\""));
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

    // ------------------------------------------------------------------------
    // Card
    // PaymentSetup.payment_methods.card: the card data lives under details,
    // token is an object, plus schemes and preferred_scheme. The flat
    // accessors are deprecated and now write inside details.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripCardWithEveryProperty() {
        final Card original = buildSampleCard();

        final String json = serializer.toJson(original);
        final JsonObject card = JsonParser.parseString(json).getAsJsonObject();
        final JsonObject details = card.getAsJsonObject("details");

        assertEquals("tok_ubfj2q76miwundwlk72vxt2i7q", card.getAsJsonObject("token").get("id").getAsString());
        assertEquals(11, details.size());
        assertEquals("4242424242424242", details.get("number").getAsString());
        assertEquals("4242", details.get("last4").getAsString());
        assertEquals("424242", details.get("bin").getAsString());
        assertEquals("visa", details.get("scheme").getAsString());
        assertEquals(12, details.get("expiry_month").getAsInt());
        assertEquals(2030, details.get("expiry_year").getAsInt());
        assertEquals("John Smith", details.get("name").getAsString());
        assertEquals("100", details.get("cvv").getAsString());
        assertTrue(details.get("stored").getAsBoolean());
        assertEquals("2030-01-01T10:00:00Z", details.get("expires_on").getAsString());
        assertTrue(details.get("allow_update").getAsBoolean());
        assertTrue(card.get("store_for_future_use").getAsBoolean());
        assertEquals("cartes_bancaires", card.getAsJsonArray("schemes").get(1).getAsString());
        assertEquals("china_union_pay", card.getAsJsonArray("schemes").get(2).getAsString());
        assertEquals("cartes_bancaires", card.get("preferred_scheme").getAsString());
        assertFalse(card.has("number"));
        assertFalse(card.has("cvv"));
        assertFalse(card.has("expiry_month"));
        assertEquals(original, serializer.fromJson(json, Card.class));
    }

    @Test
    void shouldDeserializeCardSwaggerExample() {
        final String json = "{"
                + "\"status\":\"ready\","
                + "\"flags\":[],"
                + "\"token\":{\"id\":\"tok_ubfj2q76miwundwlk72vxt2i7q\"},"
                + "\"details\":{\"last4\":\"4242\",\"bin\":\"424242\",\"scheme\":\"visa\","
                + "\"expiry_month\":12,\"expiry_year\":2030,\"expires_on\":\"2030-01-01T10:00:00Z\","
                + "\"allow_update\":true},"
                + "\"schemes\":[\"visa\",\"mastercard\"],"
                + "\"preferred_scheme\":\"visa\""
                + "}";

        final Card card = serializer.fromJson(json, Card.class);

        assertEquals(PaymentMethodStatus.READY, card.getStatus());
        assertEquals("tok_ubfj2q76miwundwlk72vxt2i7q", card.getToken().getId());
        assertEquals("4242", card.getDetails().getLast4());
        assertEquals("424242", card.getDetails().getBin());
        assertEquals("visa", card.getDetails().getScheme());
        assertEquals(Integer.valueOf(12), card.getDetails().getExpiryMonth());
        assertEquals(Integer.valueOf(2030), card.getDetails().getExpiryYear());
        assertEquals(Instant.parse("2030-01-01T10:00:00Z"), card.getDetails().getExpiresOn());
        assertEquals(Boolean.TRUE, card.getDetails().getAllowUpdate());
        assertEquals(Arrays.asList(PaymentSetupScheme.VISA, PaymentSetupScheme.MASTERCARD), card.getSchemes());
        assertEquals(PreferredSchema.VISA, card.getPreferredScheme());
    }

    @Test
    void shouldSerializeEveryPaymentSetupSchemeValue() {
        for (final PaymentSetupScheme scheme : PaymentSetupScheme.values()) {
            final Card card = new Card();
            card.setSchemes(Collections.singletonList(scheme));
            final JsonObject json = JsonParser.parseString(serializer.toJson(card)).getAsJsonObject();
            assertEquals(scheme.name().toLowerCase(), json.getAsJsonArray("schemes").get(0).getAsString());
            assertEquals(scheme, serializer.fromJson(serializer.toJson(card), Card.class).getSchemes().get(0));
        }
        assertEquals(17, PaymentSetupScheme.values().length);
    }

    @Test
    @SuppressWarnings("deprecation")
    void shouldWriteUnderDetailsThroughTheDeprecatedFlatCardAccessors() {
        final Card card = new Card();
        card.setNumber("4242424242424242");
        card.setLast4("4242");
        card.setBin("424242");
        card.setScheme("visa");
        card.setExpiryMonth(12);
        card.setExpiryYear(2030);
        card.setName("John Smith");
        card.setCvv("100");
        card.setStored(true);
        card.setExpiresOn(Instant.parse("2030-01-01T10:00:00Z"));
        card.setAllowUpdate(true);

        final JsonObject json = JsonParser.parseString(serializer.toJson(card)).getAsJsonObject();

        assertEquals(1, json.size());
        assertEquals(buildSampleCard().getDetails(), card.getDetails());
        assertEquals(11, json.getAsJsonObject("details").size());
        assertEquals("4242424242424242", card.getNumber());
        assertEquals("100", card.getCvv());
        assertEquals(Instant.parse("2030-01-01T10:00:00Z"), card.getExpiresOn());
        assertNull(new Card().getNumber());
    }

    // ------------------------------------------------------------------------
    // Instrument
    // PaymentSetupInstrument: id, the read-only type and action.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripInstrumentWithEveryProperty() {
        final Instrument original = new Instrument();
        original.setStatus(PaymentMethodStatus.READY);
        original.setFlags(Collections.emptyList());
        original.setId("src_wmlfc3zttb4uzmk6snpwb43jbi");
        original.setType("bacs");
        original.setAction(PaymentMethodAction.builder().type("set_instrument").build());

        final String json = serializer.toJson(original);
        final JsonObject instrument = JsonParser.parseString(json).getAsJsonObject();

        assertEquals("src_wmlfc3zttb4uzmk6snpwb43jbi", instrument.get("id").getAsString());
        assertEquals("bacs", instrument.get("type").getAsString());
        assertEquals("set_instrument", instrument.getAsJsonObject("action").get("type").getAsString());
        assertEquals(original, serializer.fromJson(json, Instrument.class));
    }

    @Test
    void shouldDeserializeInstrumentSwaggerExample() {
        final String json = "{\"status\":\"ready\",\"flags\":[],\"id\":\"src_wmlfc3zttb4uzmk6snpwb43jbi\","
                + "\"type\":\"bacs\",\"action\":{\"type\":\"set_instrument\"}}";

        final Instrument instrument = serializer.fromJson(json, Instrument.class);

        assertEquals("src_wmlfc3zttb4uzmk6snpwb43jbi", instrument.getId());
        assertEquals("bacs", instrument.getType());
        assertEquals("set_instrument", instrument.getAction().getType());
    }

    private static Card buildSampleCard() {
        final Card c = new Card();
        c.setStatus(PaymentMethodStatus.READY);
        c.setFlags(Collections.emptyList());
        c.setToken(CardToken.builder().id("tok_ubfj2q76miwundwlk72vxt2i7q").build());
        c.setDetails(CardDetails.builder()
                .number("4242424242424242")
                .last4("4242")
                .bin("424242")
                .scheme("visa")
                .expiryMonth(12)
                .expiryYear(2030)
                .name("John Smith")
                .cvv("100")
                .stored(true)
                .expiresOn(Instant.parse("2030-01-01T10:00:00Z"))
                .allowUpdate(true)
                .build());
        c.setStoreForFutureUse(true);
        c.setSchemes(Arrays.asList(PaymentSetupScheme.VISA, PaymentSetupScheme.CARTES_BANCAIRES, PaymentSetupScheme.CHINA_UNION_PAY));
        c.setPreferredScheme(PreferredSchema.CARTES_BANCAIRES);
        return c;
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
        s.setStatus(PaymentMethodStatus.INITIALIZATION_REQUIRED);
        s.setFlags(Collections.emptyList());
        s.setInitialization(PaymentMethodInitialization.DISABLED);
        s.setOtp("999888");
        s.setAction(PaymentMethodAction.builder().type("otp").build());
        return s;
    }

    private static Tabby buildSampleTabby() {
        final Tabby t = new Tabby();
        t.setStatus(PaymentMethodStatus.INVALID);
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
