package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.MerchantAccount;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach.Ach;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach.AchAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach.AchAccountHolderType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach.AchAccountType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach.AchIdentification;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alipay.AlipayCn;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alipay.AlipayHk;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alma.Alma;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.Bacs;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.BacsAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.BacsAccountHolderType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bancontact.Bancontact;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.benefit.Benefit;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.blik.Blik;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.OsType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodStatus;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.TerminalType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.dana.Dana;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.eps.Eps;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.gcash.GCash;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ideal.Ideal;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.kakaopay.KakaoPay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.knet.Knet;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.knet.KnetLanguage;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.mbway.MBWay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.mobilepay.MobilePay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.multibanco.Multibanco;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.octopus.Octopus;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.p24.P24;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.p24.P24AccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paynow.PayNow;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.qpay.Qpay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa.Sepa;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa.SepaAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa.SepaAccountHolderType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa.SepaMandate;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa.SepaMandateType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sequra.Sequra;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.swish.Swish;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.swish.SwishAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tng.Tng;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.truemoney.TrueMoney;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.twint.Twint;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.vipps.Vipps;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.wechatpay.WeChatPay;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Serialization tests for the Payment Setup payment methods that had no dedicated test, and for
 * {@link MerchantAccount#getLastPaymentAmount()}. Every method gets a round trip with every
 * property, written under its literal wire key inside {@link PaymentMethods}, and a test that
 * reads the spec example. The spec examples use the literal example values of the Payment Setups
 * schemas; properties the spec gives no example for are covered by the round trip only.
 */
class PaymentSetupsPaymentMethodsSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------------
    // Methods that only carry status and flags
    // PaymentSetup.payment_methods.{alma, benefit, eps, mbway, mobilepay,
    // octopus, paynow, sequra, twint, vipps, wechatpay}.
    // ------------------------------------------------------------------------

    private static Stream<Arguments> statusAndFlagsMethods() {
        return Stream.of(
                statusAndFlags("alma", Alma::new, PaymentMethods::getAlma,
                        (b, m) -> b.alma((Alma) m)),
                statusAndFlags("benefit", Benefit::new, PaymentMethods::getBenefit,
                        (b, m) -> b.benefit((Benefit) m)),
                statusAndFlags("eps", Eps::new, PaymentMethods::getEps,
                        (b, m) -> b.eps((Eps) m)),
                statusAndFlags("mbway", MBWay::new, PaymentMethods::getMbway,
                        (b, m) -> b.mbway((MBWay) m)),
                statusAndFlags("mobilepay", MobilePay::new, PaymentMethods::getMobilepay,
                        (b, m) -> b.mobilepay((MobilePay) m)),
                statusAndFlags("octopus", Octopus::new, PaymentMethods::getOctopus,
                        (b, m) -> b.octopus((Octopus) m)),
                statusAndFlags("paynow", PayNow::new, PaymentMethods::getPaynow,
                        (b, m) -> b.paynow((PayNow) m)),
                statusAndFlags("sequra", Sequra::new, PaymentMethods::getSequra,
                        (b, m) -> b.sequra((Sequra) m)),
                statusAndFlags("twint", Twint::new, PaymentMethods::getTwint,
                        (b, m) -> b.twint((Twint) m)),
                statusAndFlags("vipps", Vipps::new, PaymentMethods::getVipps,
                        (b, m) -> b.vipps((Vipps) m)),
                statusAndFlags("wechatpay", WeChatPay::new, PaymentMethods::getWechatpay,
                        (b, m) -> b.wechatpay((WeChatPay) m)));
    }

    private static Arguments statusAndFlags(final String wireKey,
                                            final Supplier<? extends PaymentMethodBase> factory,
                                            final Function<PaymentMethods, ? extends PaymentMethodBase> getter,
                                            final BiConsumer<PaymentMethods.PaymentMethodsBuilder, PaymentMethodBase> setter) {
        return Arguments.of(wireKey, factory, getter, setter);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("statusAndFlagsMethods")
    void shouldRoundTripStatusAndFlagsMethodUnderItsWireKey(
            final String wireKey,
            final Supplier<? extends PaymentMethodBase> factory,
            final Function<PaymentMethods, ? extends PaymentMethodBase> getter,
            final BiConsumer<PaymentMethods.PaymentMethodsBuilder, PaymentMethodBase> setter) {
        final PaymentMethodBase original = factory.get();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        final PaymentMethods.PaymentMethodsBuilder builder = PaymentMethods.builder();
        setter.accept(builder, original);

        final String json = serializer.toJson(builder.build());
        final JsonObject container = JsonParser.parseString(json).getAsJsonObject();
        final JsonObject method = container.getAsJsonObject(wireKey);

        assertEquals(1, container.size());
        assertEquals(2, method.size());
        assertEquals("action_required", method.get("status").getAsString());
        assertEquals("customer.property_required", method.getAsJsonArray("flags").get(0).getAsString());
        assertFalse(method.has("initialization"));
        assertEquals(original, getter.apply(serializer.fromJson(json, PaymentMethods.class)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("statusAndFlagsMethods")
    void shouldDeserializeStatusAndFlagsMethodSwaggerExample(
            final String wireKey,
            final Supplier<? extends PaymentMethodBase> factory,
            final Function<PaymentMethods, ? extends PaymentMethodBase> getter,
            final BiConsumer<PaymentMethods.PaymentMethodsBuilder, PaymentMethodBase> setter) {
        final String json = "{\"" + wireKey + "\":{\"status\":\"ready\",\"flags\":[]}}";

        final PaymentMethodBase method = getter.apply(serializer.fromJson(json, PaymentMethods.class));

        assertInstanceOf(factory.get().getClass(), method);
        assertEquals(PaymentMethodStatus.READY, method.getStatus());
        assertTrue(method.getFlags().isEmpty());
    }

    // ------------------------------------------------------------------------
    // Methods with terminal_type and os_type
    // PaymentSetup.payment_methods.{alipay_cn, alipay_hk, gcash, tng, dana,
    // kakaopay, truemoney}: status, flags, terminal_type and os_type.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripAlipayCnWithEveryProperty() {
        final AlipayCn original = new AlipayCn();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setTerminalType(TerminalType.APP);
        original.setOsType(OsType.IOS);

        final String json = serializer.toJson(PaymentMethods.builder().alipayCn(original).build());

        assertTerminalMethodKeys(json, "alipay_cn");
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getAlipayCn());
    }

    @Test
    void shouldDeserializeAlipayCnSwaggerExample() {
        final AlipayCn method = serializer.fromJson(terminalSwaggerExample("alipay_cn"), PaymentMethods.class)
                .getAlipayCn();

        assertEquals(PaymentMethodStatus.READY, method.getStatus());
        assertEquals(TerminalType.WEB, method.getTerminalType());
        assertEquals(OsType.ANDROID, method.getOsType());
    }

    @Test
    void shouldRoundTripAlipayHkWithEveryProperty() {
        final AlipayHk original = new AlipayHk();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setTerminalType(TerminalType.APP);
        original.setOsType(OsType.IOS);

        final String json = serializer.toJson(PaymentMethods.builder().alipayHk(original).build());

        assertTerminalMethodKeys(json, "alipay_hk");
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getAlipayHk());
    }

    @Test
    void shouldDeserializeAlipayHkSwaggerExample() {
        final AlipayHk method = serializer.fromJson(terminalSwaggerExample("alipay_hk"), PaymentMethods.class)
                .getAlipayHk();

        assertEquals(PaymentMethodStatus.READY, method.getStatus());
        assertEquals(TerminalType.WEB, method.getTerminalType());
        assertEquals(OsType.ANDROID, method.getOsType());
    }

    @Test
    void shouldRoundTripGCashWithEveryProperty() {
        final GCash original = new GCash();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setTerminalType(TerminalType.APP);
        original.setOsType(OsType.IOS);

        final String json = serializer.toJson(PaymentMethods.builder().gcash(original).build());

        assertTerminalMethodKeys(json, "gcash");
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getGcash());
    }

    @Test
    void shouldDeserializeGCashSwaggerExample() {
        final GCash method = serializer.fromJson(terminalSwaggerExample("gcash"), PaymentMethods.class).getGcash();

        assertEquals(PaymentMethodStatus.READY, method.getStatus());
        assertEquals(TerminalType.WEB, method.getTerminalType());
        assertEquals(OsType.ANDROID, method.getOsType());
    }

    @Test
    void shouldRoundTripTngWithEveryProperty() {
        final Tng original = new Tng();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setTerminalType(TerminalType.WAP);
        original.setOsType(OsType.IOS);

        final String json = serializer.toJson(PaymentMethods.builder().tng(original).build());

        assertEquals("wap", JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("tng")
                .get("terminal_type").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getTng());
    }

    @Test
    void shouldDeserializeTngSwaggerExample() {
        final Tng method = serializer.fromJson(terminalSwaggerExample("tng"), PaymentMethods.class).getTng();

        assertEquals(PaymentMethodStatus.READY, method.getStatus());
        assertEquals(TerminalType.WEB, method.getTerminalType());
        assertEquals(OsType.ANDROID, method.getOsType());
    }

    @Test
    void shouldRoundTripDanaWithEveryProperty() {
        final Dana original = new Dana();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setTerminalType(TerminalType.APP);
        original.setOsType(OsType.IOS);

        final String json = serializer.toJson(PaymentMethods.builder().dana(original).build());

        assertTerminalMethodKeys(json, "dana");
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getDana());
    }

    @Test
    void shouldDeserializeDanaSwaggerExample() {
        final Dana method = serializer.fromJson(terminalSwaggerExample("dana"), PaymentMethods.class).getDana();

        assertEquals(PaymentMethodStatus.READY, method.getStatus());
        assertEquals(TerminalType.WEB, method.getTerminalType());
        assertEquals(OsType.ANDROID, method.getOsType());
    }

    @Test
    void shouldRoundTripKakaoPayWithEveryProperty() {
        final KakaoPay original = new KakaoPay();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setTerminalType(TerminalType.APP);
        original.setOsType(OsType.IOS);

        final String json = serializer.toJson(PaymentMethods.builder().kakaopay(original).build());

        assertTerminalMethodKeys(json, "kakaopay");
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getKakaopay());
    }

    @Test
    void shouldDeserializeKakaoPaySwaggerExample() {
        final KakaoPay method = serializer.fromJson(terminalSwaggerExample("kakaopay"), PaymentMethods.class)
                .getKakaopay();

        assertEquals(PaymentMethodStatus.READY, method.getStatus());
        assertEquals(TerminalType.WEB, method.getTerminalType());
        assertEquals(OsType.ANDROID, method.getOsType());
    }

    @Test
    void shouldRoundTripTrueMoneyWithEveryProperty() {
        final TrueMoney original = new TrueMoney();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setTerminalType(TerminalType.APP);
        original.setOsType(OsType.IOS);

        final String json = serializer.toJson(PaymentMethods.builder().truemoney(original).build());

        assertTerminalMethodKeys(json, "truemoney");
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getTruemoney());
    }

    @Test
    void shouldDeserializeTrueMoneySwaggerExample() {
        final TrueMoney method = serializer.fromJson(terminalSwaggerExample("truemoney"), PaymentMethods.class)
                .getTruemoney();

        assertEquals(PaymentMethodStatus.READY, method.getStatus());
        assertEquals(TerminalType.WEB, method.getTerminalType());
        assertEquals(OsType.ANDROID, method.getOsType());
    }

    // ------------------------------------------------------------------------
    // Ach
    // PaymentSetup.payment_methods.ach: account_type, account_holder (with
    // identification), account_number, bank_code and country.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripAchWithEveryProperty() {
        final Ach original = new Ach();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setAccountType(AchAccountType.SAVINGS);
        original.setAccountHolder(AchAccountHolder.builder()
                .type(AchAccountHolderType.INDIVIDUAL)
                .firstName("John")
                .lastName("Smith")
                .companyName("Acme Corporation")
                .dateOfBirth("1985-05-15")
                .identification(AchIdentification.builder()
                        .type("ssn")
                        .issuingCountry("US")
                        .number("123456789")
                        .build())
                .build());
        original.setAccountNumber("4099999992");
        original.setBankCode("011075150");
        original.setCountry("US");

        final String json = serializer.toJson(PaymentMethods.builder().ach(original).build());
        final JsonObject ach = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("ach");
        final JsonObject accountHolder = ach.getAsJsonObject("account_holder");
        final JsonObject identification = accountHolder.getAsJsonObject("identification");

        assertEquals(7, ach.size());
        assertEquals("action_required", ach.get("status").getAsString());
        assertEquals("customer.property_required", ach.getAsJsonArray("flags").get(0).getAsString());
        assertEquals("savings", ach.get("account_type").getAsString());
        assertEquals("4099999992", ach.get("account_number").getAsString());
        assertEquals("011075150", ach.get("bank_code").getAsString());
        assertEquals("US", ach.get("country").getAsString());
        assertEquals(6, accountHolder.size());
        assertEquals("individual", accountHolder.get("type").getAsString());
        assertEquals("John", accountHolder.get("first_name").getAsString());
        assertEquals("Smith", accountHolder.get("last_name").getAsString());
        assertEquals("Acme Corporation", accountHolder.get("company_name").getAsString());
        assertEquals("1985-05-15", accountHolder.get("date_of_birth").getAsString());
        assertEquals(3, identification.size());
        assertEquals("ssn", identification.get("type").getAsString());
        assertEquals("US", identification.get("issuing_country").getAsString());
        assertEquals("123456789", identification.get("number").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getAch());
    }

    @Test
    void shouldDeserializeAchSwaggerExample() {
        final String json = "{\"ach\":{\"status\":\"ready\",\"flags\":[],\"account_type\":\"current\","
                + "\"account_holder\":{\"type\":\"corporate\"}}}";

        final Ach ach = serializer.fromJson(json, PaymentMethods.class).getAch();

        assertEquals(PaymentMethodStatus.READY, ach.getStatus());
        assertTrue(ach.getFlags().isEmpty());
        assertEquals(AchAccountType.CURRENT, ach.getAccountType());
        assertEquals(AchAccountHolderType.CORPORATE, ach.getAccountHolder().getType());
    }

    @Test
    void shouldWriteAndReadEveryAchEnumValue() {
        assertEquals("\"savings\"", serializer.toJson(AchAccountType.SAVINGS));
        assertEquals("\"current\"", serializer.toJson(AchAccountType.CURRENT));
        assertEquals("\"cash\"", serializer.toJson(AchAccountType.CASH));
        assertEquals(3, AchAccountType.values().length);
        assertEquals("\"individual\"", serializer.toJson(AchAccountHolderType.INDIVIDUAL));
        assertEquals("\"corporate\"", serializer.toJson(AchAccountHolderType.CORPORATE));
        assertEquals("\"government\"", serializer.toJson(AchAccountHolderType.GOVERNMENT));
        assertEquals(3, AchAccountHolderType.values().length);
        assertEquals(AchAccountHolderType.GOVERNMENT,
                serializer.fromJson("\"government\"", AchAccountHolderType.class));
    }

    // ------------------------------------------------------------------------
    // Sepa
    // PaymentSetup.payment_methods.sepa: account_holder, account_number,
    // country, currency and mandate.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripSepaWithEveryProperty() {
        final Sepa original = new Sepa();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setAccountHolder(SepaAccountHolder.builder()
                .type(SepaAccountHolderType.CORPORATE)
                .firstName("John")
                .lastName("Smith")
                .companyName("Acme Corporation")
                .build());
        original.setAccountNumber("DE89370400440532013000");
        original.setCountry("DE");
        original.setCurrency(Currency.EUR);
        original.setMandate(SepaMandate.builder()
                .id("mandate_123")
                .type(SepaMandateType.B2B)
                .dateOfSignature(LocalDate.of(2025, 1, 15))
                .build());

        final String json = serializer.toJson(PaymentMethods.builder().sepa(original).build());
        final JsonObject sepa = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("sepa");
        final JsonObject accountHolder = sepa.getAsJsonObject("account_holder");
        final JsonObject mandate = sepa.getAsJsonObject("mandate");

        assertEquals(7, sepa.size());
        assertEquals("action_required", sepa.get("status").getAsString());
        assertEquals("DE89370400440532013000", sepa.get("account_number").getAsString());
        assertEquals("DE", sepa.get("country").getAsString());
        assertEquals("EUR", sepa.get("currency").getAsString());
        assertEquals(4, accountHolder.size());
        assertEquals("corporate", accountHolder.get("type").getAsString());
        assertEquals("John", accountHolder.get("first_name").getAsString());
        assertEquals("Smith", accountHolder.get("last_name").getAsString());
        assertEquals("Acme Corporation", accountHolder.get("company_name").getAsString());
        assertEquals(3, mandate.size());
        assertEquals("mandate_123", mandate.get("id").getAsString());
        assertEquals("b2b", mandate.get("type").getAsString());
        assertEquals("2025-01-15", mandate.get("date_of_signature").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getSepa());
    }

    @Test
    void shouldDeserializeSepaSwaggerExample() {
        final String json = "{\"sepa\":{\"status\":\"ready\",\"flags\":[],"
                + "\"account_holder\":{\"type\":\"individual\"},"
                + "\"mandate\":{\"type\":\"core\"}}}";

        final Sepa sepa = serializer.fromJson(json, PaymentMethods.class).getSepa();

        assertEquals(PaymentMethodStatus.READY, sepa.getStatus());
        assertTrue(sepa.getFlags().isEmpty());
        assertEquals(SepaAccountHolderType.INDIVIDUAL, sepa.getAccountHolder().getType());
        assertEquals(SepaMandateType.CORE, sepa.getMandate().getType());
    }

    // ------------------------------------------------------------------------
    // Swish
    // PaymentSetup.payment_methods.swish: billing_descriptor and
    // account_holder.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripSwishWithEveryProperty() {
        final Swish original = new Swish();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setBillingDescriptor("Acme order 1234");
        original.setAccountHolder(SwishAccountHolder.builder().firstName("John").lastName("Smith").build());

        final String json = serializer.toJson(PaymentMethods.builder().swish(original).build());
        final JsonObject swish = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("swish");
        final JsonObject accountHolder = swish.getAsJsonObject("account_holder");

        assertEquals(4, swish.size());
        assertEquals("action_required", swish.get("status").getAsString());
        assertEquals("Acme order 1234", swish.get("billing_descriptor").getAsString());
        assertEquals(2, accountHolder.size());
        assertEquals("John", accountHolder.get("first_name").getAsString());
        assertEquals("Smith", accountHolder.get("last_name").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getSwish());
    }

    @Test
    void shouldDeserializeSwishSwaggerExample() {
        final String json = "{\"swish\":{\"status\":\"ready\",\"flags\":[],"
                + "\"account_holder\":{\"first_name\":\"John\",\"last_name\":\"Smith\"}}}";

        final Swish swish = serializer.fromJson(json, PaymentMethods.class).getSwish();

        assertEquals(PaymentMethodStatus.READY, swish.getStatus());
        assertEquals("John", swish.getAccountHolder().getFirstName());
        assertEquals("Smith", swish.getAccountHolder().getLastName());
    }

    // ------------------------------------------------------------------------
    // P24
    // PaymentSetup.payment_methods.p24: account_holder with name and email.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripP24WithEveryProperty() {
        final P24 original = new P24();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setAccountHolder(P24AccountHolder.builder()
                .name("John Smith")
                .email("john.smith@example.com")
                .build());

        final String json = serializer.toJson(PaymentMethods.builder().p24(original).build());
        final JsonObject p24 = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("p24");
        final JsonObject accountHolder = p24.getAsJsonObject("account_holder");

        assertEquals(3, p24.size());
        assertEquals("action_required", p24.get("status").getAsString());
        assertEquals(2, accountHolder.size());
        assertEquals("John Smith", accountHolder.get("name").getAsString());
        assertEquals("john.smith@example.com", accountHolder.get("email").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getP24());
    }

    @Test
    void shouldDeserializeP24SwaggerExample() {
        final String json = "{\"p24\":{\"status\":\"ready\",\"flags\":[]}}";

        final P24 p24 = serializer.fromJson(json, PaymentMethods.class).getP24();

        assertEquals(PaymentMethodStatus.READY, p24.getStatus());
        assertTrue(p24.getFlags().isEmpty());
    }

    // ------------------------------------------------------------------------
    // Qpay, Ideal, Knet, Bancontact, Multibanco and Blik
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripQpayWithEveryProperty() {
        final Qpay original = new Qpay();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setNationalId("21234567890");
        original.setDescription("Order 1234");

        final String json = serializer.toJson(PaymentMethods.builder().qpay(original).build());
        final JsonObject qpay = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("qpay");

        assertEquals(4, qpay.size());
        assertEquals("action_required", qpay.get("status").getAsString());
        assertEquals("21234567890", qpay.get("national_id").getAsString());
        assertEquals("Order 1234", qpay.get("description").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getQpay());
    }

    @Test
    void shouldDeserializeQpaySwaggerExample() {
        final String json = "{\"qpay\":{\"status\":\"ready\",\"flags\":[],\"national_id\":\"21234567890\"}}";

        final Qpay qpay = serializer.fromJson(json, PaymentMethods.class).getQpay();

        assertEquals(PaymentMethodStatus.READY, qpay.getStatus());
        assertEquals("21234567890", qpay.getNationalId());
    }

    @Test
    void shouldRoundTripIdealWithEveryProperty() {
        final Ideal original = new Ideal();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setDescription("2 t-shirts");

        final String json = serializer.toJson(PaymentMethods.builder().ideal(original).build());
        final JsonObject ideal = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("ideal");

        assertEquals(3, ideal.size());
        assertEquals("action_required", ideal.get("status").getAsString());
        assertEquals("2 t-shirts", ideal.get("description").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getIdeal());
    }

    @Test
    void shouldDeserializeIdealSwaggerExample() {
        final String json = "{\"ideal\":{\"status\":\"ready\",\"flags\":[],\"description\":\"2 t-shirts\"}}";

        final Ideal ideal = serializer.fromJson(json, PaymentMethods.class).getIdeal();

        assertEquals(PaymentMethodStatus.READY, ideal.getStatus());
        assertEquals("2 t-shirts", ideal.getDescription());
    }

    @Test
    void shouldRoundTripKnetWithEveryProperty() {
        final Knet original = new Knet();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setLanguage(KnetLanguage.AR);

        final String json = serializer.toJson(PaymentMethods.builder().knet(original).build());
        final JsonObject knet = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("knet");

        assertEquals(3, knet.size());
        assertEquals("action_required", knet.get("status").getAsString());
        assertEquals("ar", knet.get("language").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getKnet());
    }

    @Test
    void shouldDeserializeKnetSwaggerExample() {
        final String json = "{\"knet\":{\"status\":\"ready\",\"flags\":[],\"language\":\"en\"}}";

        final Knet knet = serializer.fromJson(json, PaymentMethods.class).getKnet();

        assertEquals(PaymentMethodStatus.READY, knet.getStatus());
        assertEquals(KnetLanguage.EN, knet.getLanguage());
    }

    @Test
    void shouldRoundTripBancontactWithEveryProperty() {
        final Bancontact original = new Bancontact();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setAccountHolderName("John Smith");

        final String json = serializer.toJson(PaymentMethods.builder().bancontact(original).build());
        final JsonObject bancontact = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("bancontact");

        assertEquals(3, bancontact.size());
        assertEquals("action_required", bancontact.get("status").getAsString());
        assertEquals("John Smith", bancontact.get("account_holder_name").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getBancontact());
    }

    @Test
    void shouldDeserializeBancontactSwaggerExample() {
        final String json = "{\"bancontact\":{\"status\":\"ready\",\"flags\":[]}}";

        final Bancontact bancontact = serializer.fromJson(json, PaymentMethods.class).getBancontact();

        assertEquals(PaymentMethodStatus.READY, bancontact.getStatus());
        assertTrue(bancontact.getFlags().isEmpty());
    }

    @Test
    void shouldRoundTripMultibancoWithEveryProperty() {
        final Multibanco original = new Multibanco();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setAccountHolderName("John Smith");

        final String json = serializer.toJson(PaymentMethods.builder().multibanco(original).build());
        final JsonObject multibanco = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("multibanco");

        assertEquals(3, multibanco.size());
        assertEquals("action_required", multibanco.get("status").getAsString());
        assertEquals("customer.property_required", multibanco.getAsJsonArray("flags").get(0).getAsString());
        assertEquals("John Smith", multibanco.get("account_holder_name").getAsString());
        assertFalse(multibanco.has("initialization"));
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getMultibanco());
    }

    @Test
    void shouldDeserializeMultibancoSwaggerExample() {
        final String json = "{\"multibanco\":{\"status\":\"ready\",\"flags\":[]}}";

        final Multibanco multibanco = serializer.fromJson(json, PaymentMethods.class).getMultibanco();

        assertEquals(PaymentMethodStatus.READY, multibanco.getStatus());
        assertTrue(multibanco.getFlags().isEmpty());
    }

    @Test
    void shouldRoundTripBlikWithEveryProperty() {
        final Blik original = new Blik();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setPartnerCode("999111");

        final String json = serializer.toJson(PaymentMethods.builder().blik(original).build());
        final JsonObject blik = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("blik");

        assertEquals(3, blik.size());
        assertEquals("action_required", blik.get("status").getAsString());
        assertEquals("999111", blik.get("partner_code").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getBlik());
    }

    @Test
    void shouldDeserializeBlikSwaggerExample() {
        final String json = "{\"blik\":{\"status\":\"ready\",\"flags\":[],\"partner_code\":\"999111\"}}";

        final Blik blik = serializer.fromJson(json, PaymentMethods.class).getBlik();

        assertEquals(PaymentMethodStatus.READY, blik.getStatus());
        assertEquals("999111", blik.getPartnerCode());
    }

    // ------------------------------------------------------------------------
    // Bacs
    // PaymentSetup.payment_methods.bacs: status, flags, initialization and
    // the 7 Bacs properties.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripBacsWithEveryProperty() {
        final Bacs original = new Bacs();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setInitialization(PaymentMethodInitialization.ENABLED);
        original.setInstrumentId("src_wkq7552u245upl5h75x24554xy");
        original.setAccountHolder(BacsAccountHolder.builder()
                .type(BacsAccountHolderType.CORPORATE)
                .firstName("John")
                .lastName("Smith")
                .companyName("Acme Corporation")
                .email("john.smith@example.com")
                .build());
        original.setAccountNumber("55779911");
        original.setBankCode("200000");
        original.setCountry(CountryCode.GB);
        original.setCurrency("GBP");
        original.setAllowPartialMatch(true);

        final String json = serializer.toJson(PaymentMethods.builder().bacs(original).build());
        final JsonObject bacs = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("bacs");
        final JsonObject accountHolder = bacs.getAsJsonObject("account_holder");

        assertEquals(10, bacs.size());
        assertEquals("action_required", bacs.get("status").getAsString());
        assertEquals("enabled", bacs.get("initialization").getAsString());
        assertEquals("src_wkq7552u245upl5h75x24554xy", bacs.get("instrument_id").getAsString());
        assertEquals("55779911", bacs.get("account_number").getAsString());
        assertEquals("200000", bacs.get("bank_code").getAsString());
        assertEquals("GB", bacs.get("country").getAsString());
        assertEquals("GBP", bacs.get("currency").getAsString());
        assertTrue(bacs.get("allow_partial_match").getAsBoolean());
        assertEquals(5, accountHolder.size());
        assertEquals("corporate", accountHolder.get("type").getAsString());
        assertEquals("John", accountHolder.get("first_name").getAsString());
        assertEquals("Smith", accountHolder.get("last_name").getAsString());
        assertEquals("Acme Corporation", accountHolder.get("company_name").getAsString());
        assertEquals("john.smith@example.com", accountHolder.get("email").getAsString());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getBacs());
    }

    @Test
    void shouldDeserializeBacsSwaggerExample() {
        final String json = "{\"bacs\":{\"status\":\"ready\",\"flags\":[],\"initialization\":\"disabled\","
                + "\"instrument_id\":\"src_wkq7552u245upl5h75x24554xy\","
                + "\"account_holder\":{\"type\":\"individual\",\"email\":\"john.smith@example.com\"},"
                + "\"allow_partial_match\":false}}";

        final Bacs bacs = serializer.fromJson(json, PaymentMethods.class).getBacs();

        assertEquals(PaymentMethodStatus.READY, bacs.getStatus());
        assertEquals(PaymentMethodInitialization.DISABLED, bacs.getInitialization());
        assertEquals("src_wkq7552u245upl5h75x24554xy", bacs.getInstrumentId());
        assertEquals(BacsAccountHolderType.INDIVIDUAL, bacs.getAccountHolder().getType());
        assertEquals("john.smith@example.com", bacs.getAccountHolder().getEmail());
        assertEquals(Boolean.FALSE, bacs.getAllowPartialMatch());
    }

    // ------------------------------------------------------------------------
    // MerchantAccount.last_payment_amount
    // PaymentSetupMerchantAccount.last_payment_amount: the spec types it as a
    // number (example 55.99), but the API stores it as an integer, so the SDK
    // keeps Long.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripLastPaymentAmountUnderItsWireKey() {
        final MerchantAccount original = MerchantAccount.builder().lastPaymentAmount(55L).build();

        final String json = serializer.toJson(original);

        assertEquals("{\"last_payment_amount\":55}", json);
        assertEquals(original, serializer.fromJson(json, MerchantAccount.class));
    }

    @Test
    void shouldDeserializeMerchantAccountSwaggerExample() {
        final String json = "{\"id\":\"1234\",\"returning_customer\":true,\"total_order_count\":6,"
                + "\"last_payment_amount\":55}";

        final MerchantAccount account = serializer.fromJson(json, MerchantAccount.class);

        assertNotNull(account);
        assertEquals("1234", account.getId());
        assertEquals(Boolean.TRUE, account.getReturningCustomer());
        assertEquals(Integer.valueOf(6), account.getTotalOrderCount());
        assertEquals(Long.valueOf(55L), account.getLastPaymentAmount());
    }

    // ------------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------------

    private static String terminalSwaggerExample(final String wireKey) {
        return "{\"" + wireKey + "\":{\"status\":\"ready\",\"flags\":[],"
                + "\"terminal_type\":\"web\",\"os_type\":\"android\"}}";
    }

    private static void assertTerminalMethodKeys(final String json, final String wireKey) {
        final JsonObject container = JsonParser.parseString(json).getAsJsonObject();
        final JsonObject method = container.getAsJsonObject(wireKey);
        assertEquals(1, container.size());
        assertEquals(4, method.size());
        assertEquals("action_required", method.get("status").getAsString());
        assertEquals("customer.property_required", method.getAsJsonArray("flags").get(0).getAsString());
        assertEquals("app", method.get("terminal_type").getAsString());
        assertEquals("ios", method.get("os_type").getAsString());
    }
}
