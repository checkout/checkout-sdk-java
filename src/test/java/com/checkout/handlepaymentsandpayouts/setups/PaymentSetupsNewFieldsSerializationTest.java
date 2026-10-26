package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.billingDescriptor.PaymentSetupBillingDescriptor;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.Customer;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerDevice;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerDeviceClient;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerEmail;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.MerchantAccount;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.AmountAllocationCommission;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.PaymentSetupAmountAllocation;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.Bacs;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.BacsAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.BacsAccountHolderType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cardpresent.CardPresent;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cardpresent.CardPresentPin;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.OsType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.KlarnaAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank.PayByBank;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stablecoin.Stablecoin;
import com.checkout.handlepaymentsandpayouts.setups.entities.presentmentDetails.PaymentSetupPresentmentDetails;
import com.checkout.handlepaymentsandpayouts.setups.entities.terminal.PaymentSetupTerminal;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.OrderSubMerchant;
import com.checkout.handlepaymentsandpayouts.setups.entities.settings.Settings;
import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.Order;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodStatus;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tamara.Tamara;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tamara.TamaraRiskAssessment;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentType.PaymentSetupPaymentType;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.contexts.PaymentContextsItems;
import com.checkout.payments.contexts.PaymentContextsItemsType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentSetupsNewFieldsSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeBillingDescriptorPresentmentAndTerminal() {
        assertTrue(serializer.toJson(PaymentSetupBillingDescriptor.builder()
                .name("Checkout.com").city("London").reference("Order 123").build())
                .contains("\"reference\""));
        assertTrue(serializer.toJson(PaymentSetupPresentmentDetails.builder()
                .amount(110L).currency(Currency.EUR).build()).contains("\"amount\""));
        assertTrue(serializer.toJson(PaymentSetupTerminal.builder().id("12345678").build())
                .contains("12345678"));
    }

    @Test
    void shouldSerializeAmountAllocationWithCommission() {
        final PaymentSetupAmountAllocation allocation = PaymentSetupAmountAllocation.builder()
                .id("ent_test")
                .amount(1000L)
                .reference("ORD-1")
                .commission(AmountAllocationCommission.builder().amount(100L).percentage(1.5).build())
                .build();

        final String json = serializer.toJson(allocation);

        assertTrue(json.contains("\"commission\""));
        assertTrue(json.contains("\"percentage\""));
    }

    // card_present is deprecated and must still be sent while it exists.
    @SuppressWarnings("deprecation")
    @Test
    void shouldSerializeNewPaymentMethods() {
        final Bacs bacs = new Bacs();
        bacs.setInstrumentId("src_test");
        bacs.setCountry(CountryCode.GB);
        bacs.setCurrency("GBP");
        bacs.setAllowPartialMatch(true);
        bacs.setAccountHolder(BacsAccountHolder.builder()
                .type(BacsAccountHolderType.INDIVIDUAL).firstName("John").lastName("Smith").build());

        final String bacsJson = serializer.toJson(bacs);
        assertTrue(bacsJson.contains("\"account_holder\""));
        assertTrue(bacsJson.contains("\"allow_partial_match\""));
        assertTrue(bacsJson.contains("individual"));

        final CardPresent cardPresent = new CardPresent();
        cardPresent.setEntryMode("contactless");
        cardPresent.setPin(CardPresentPin.builder().keySetId("k").block("b").blockFormat("f").build());
        assertTrue(serializer.toJson(cardPresent).contains("\"entry_mode\""));

        final PaymentMethods methods = PaymentMethods.builder()
                .bacs(bacs)
                .cardPresent(cardPresent)
                .payByBank(new PayByBank())
                .stablecoin(new Stablecoin())
                .build();
        final String methodsJson = serializer.toJson(methods);
        assertTrue(methodsJson.contains("\"bacs\""));
        assertTrue(methodsJson.contains("\"card_present\""));
        assertTrue(methodsJson.contains("\"paybybank\""));
        assertFalse(methodsJson.contains("pay_by_bank"));
        assertTrue(methodsJson.contains("\"stablecoin\""));
    }

    @Test
    void shouldDeserializePayByBankActionWithBanks() {
        final String json = "{\"bank_id\":\"ob-natwest\",\"action\":{\"type\":\"select_bank\","
                + "\"banks\":[{\"bank_id\":\"ob-natwest\",\"display_name\":\"NatWest\",\"available\":true}]}}";

        final PayByBank payByBank = serializer.fromJson(json, PayByBank.class);

        assertNotNull(payByBank.getAction());
        assertEquals("select_bank", payByBank.getAction().getType());
        assertEquals(1, payByBank.getAction().getBanks().size());
        assertEquals("NatWest", payByBank.getAction().getBanks().get(0).getDisplayName());
    }

    @Test
    void shouldSerializeKlarnaAccountHolderName() {
        final Klarna klarna = new Klarna();
        klarna.setAccountHolder(KlarnaAccountHolder.builder().name("John Smith").build());

        final String json = serializer.toJson(klarna);

        assertTrue(json.contains("\"account_holder\""));
        assertTrue(json.contains("John Smith"));
    }

    @Test
    void shouldSerializeOrderAmountFields() {
        final com.checkout.handlepaymentsandpayouts.setups.entities.order.Order order =
                com.checkout.handlepaymentsandpayouts.setups.entities.order.Order.builder()
                        .invoiceId("inv_123")
                        .shippingAmount(500L)
                        .surchargeAmount(150L)
                        .taxAmount(200L)
                        .tippingAmount(100L)
                        .discountAmount(50L)
                        .build();

        final String json = serializer.toJson(order);

        assertTrue(json.contains("\"invoice_id\""));
        assertTrue(json.contains("\"shipping_amount\""));
        assertTrue(json.contains("\"surcharge_amount\""));
        assertTrue(json.contains("\"tax_amount\""));
        assertTrue(json.contains("\"tipping_amount\""));
    }

    // ------------------------------------------------------------------------
    // Customer
    // PaymentSetup.customer: all 8 properties, including id, country and
    // tax_number, which the model did not carry before.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripCustomerWithEveryProperty() {
        final Customer original = Customer.builder()
                .id("cus_123456789")
                .country(CountryCode.GB)
                .taxNumber("GB123456789")
                .name("John Smith")
                .email(CustomerEmail.builder().address("johnsmith@example.com").verified(true).build())
                .phone(Phone.builder().countryCode("+44").number("207 946 0000").build())
                .device(CustomerDevice.builder()
                        .locale("en_GB")
                        .fingerprint("fp_abc123xyz")
                        .ipv4("203.0.113.0")
                        .ipv6("2001:db8:85a3::8a2e:370:7334")
                        .client(CustomerDeviceClient.WEB)
                        .os(OsType.ANDROID)
                        .build())
                .merchantAccount(MerchantAccount.builder()
                        .id("acc_123")
                        .registrationDate(LocalDate.of(2023, 1, 15))
                        .lastModified(LocalDate.of(2024, 3, 10))
                        .returningCustomer(true)
                        .firstTransactionDate(LocalDate.of(2023, 2, 20))
                        .lastTransactionDate(LocalDate.of(2024, 3, 9))
                        .totalOrderCount(5)
                        .lastPaymentAmount(1000L)
                        .build())
                .build();

        final String json = serializer.toJson(original);
        final JsonObject customer = JsonParser.parseString(json).getAsJsonObject();

        assertEquals(8, customer.size());
        assertEquals("cus_123456789", customer.get("id").getAsString());
        assertEquals("GB", customer.get("country").getAsString());
        assertEquals("GB123456789", customer.get("tax_number").getAsString());
        assertFalse(customer.has("taxNumber"));
        assertEquals(original, serializer.fromJson(json, Customer.class));
    }

    @Test
    void shouldDeserializeCustomerSwaggerExample() {
        final String json = "{\"country\":\"GB\",\"id\":\"cus_123456789\","
                + "\"email\":{\"address\":\"johnsmith@example.com\",\"verified\":true},"
                + "\"name\":\"John Smith\",\"tax_number\":\"GB123456789\","
                + "\"phone\":{\"country_code\":\"+44\",\"number\":\"207 946 0000\"},"
                + "\"device\":{\"locale\":\"en_GB\"}}";

        final Customer customer = serializer.fromJson(json, Customer.class);

        assertEquals(CountryCode.GB, customer.getCountry());
        assertEquals("cus_123456789", customer.getId());
        assertEquals("GB123456789", customer.getTaxNumber());
        assertEquals("John Smith", customer.getName());
        assertEquals("johnsmith@example.com", customer.getEmail().getAddress());
        assertEquals(Boolean.TRUE, customer.getEmail().getVerified());
        assertEquals("+44", customer.getPhone().getCountryCode());
        assertEquals("207 946 0000", customer.getPhone().getNumber());
        assertEquals("en_GB", customer.getDevice().getLocale());
    }

    // ------------------------------------------------------------------------
    // Settings
    // PaymentSetup.settings: all 4 properties. capture and
    // excluded_payment_methods were missing from the model.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripSettingsWithEveryProperty() {
        final Settings original = Settings.builder()
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/fail")
                .capture(false)
                .excludedPaymentMethods(Arrays.asList("card", "klarna", "stcpay"))
                .build();

        final String json = serializer.toJson(original);
        final JsonObject settings = JsonParser.parseString(json).getAsJsonObject();

        assertEquals(4, settings.size());
        assertEquals("https://example.com/payments/success", settings.get("success_url").getAsString());
        assertEquals("https://example.com/payments/fail", settings.get("failure_url").getAsString());
        assertFalse(settings.get("capture").getAsBoolean());
        assertEquals(3, settings.getAsJsonArray("excluded_payment_methods").size());
        assertEquals("klarna", settings.getAsJsonArray("excluded_payment_methods").get(1).getAsString());
        assertEquals(original, serializer.fromJson(json, Settings.class));
    }

    @Test
    void shouldDeserializeSettingsSwaggerExample() {
        final String json = "{\"success_url\":\"https://example.com/payments/success\","
                + "\"failure_url\":\"https://example.com/payments/fail\","
                + "\"capture\":true,"
                + "\"excluded_payment_methods\":[\"card\",\"klarna\",\"stcpay\"]}";

        final Settings settings = serializer.fromJson(json, Settings.class);

        assertEquals("https://example.com/payments/success", settings.getSuccessUrl());
        assertEquals("https://example.com/payments/fail", settings.getFailureUrl());
        assertEquals(Boolean.TRUE, settings.getCapture());
        assertEquals(Arrays.asList("card", "klarna", "stcpay"), settings.getExcludedPaymentMethods());
    }

    // ------------------------------------------------------------------------
    // Metadata and auto_confirm
    // PaymentSetup.metadata (top level, request and response) and
    // PaymentSetup.payment_methods.auto_confirm, both missing from the model.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeMetadataAndAutoConfirmOnTheRequest() {
        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("coupon_code", "NY2018");
        metadata.put("partner_id", 123989.5);
        metadata.put("vip", true);
        final PaymentSetupsRequest original = PaymentSetupsRequest.builder()
                .processingChannelId("pc_q4dbxom5jbgudnjzjpz7j2z6uq")
                .amount(1000L)
                .currency(Currency.GBP)
                .paymentMethods(PaymentMethods.builder().autoConfirm("card").build())
                .metadata(metadata)
                .build();

        final String json = serializer.toJson(original);
        final JsonObject request = JsonParser.parseString(json).getAsJsonObject();

        assertEquals("card", request.getAsJsonObject("payment_methods").get("auto_confirm").getAsString());
        assertFalse(request.getAsJsonObject("payment_methods").has("autoConfirm"));
        assertEquals("NY2018", request.getAsJsonObject("metadata").get("coupon_code").getAsString());
        assertEquals(123989.5, request.getAsJsonObject("metadata").get("partner_id").getAsDouble());
        assertTrue(request.getAsJsonObject("metadata").get("vip").getAsBoolean());

        final PaymentSetupsRequest restored = serializer.fromJson(json, PaymentSetupsRequest.class);
        assertEquals("card", restored.getPaymentMethods().getAutoConfirm());
        assertEquals(metadata, restored.getMetadata());
    }

    @Test
    void shouldDeserializeMetadataAndAutoConfirmOnTheResponses() {
        final String json = "{\"id\":\"ps_2Un2jYAVxLsfUfpOvLhEHRW8Ft1\","
                + "\"payment_methods\":{\"auto_confirm\":\"card\"},"
                + "\"metadata\":{\"coupon_code\":\"NY2018\"}}";

        final PaymentSetupsResponse response = serializer.fromJson(json, PaymentSetupsResponse.class);
        final PaymentSetupsConfirmResponse confirm = serializer.fromJson(json, PaymentSetupsConfirmResponse.class);

        assertEquals("card", response.getPaymentMethods().getAutoConfirm());
        assertEquals("NY2018", response.getMetadata().get("coupon_code"));
        assertEquals("card", confirm.getPaymentMethods().getAutoConfirm());
        assertEquals("NY2018", confirm.getMetadata().get("coupon_code"));
    }

    // ------------------------------------------------------------------------
    // OrderSubMerchant
    // PaymentSetupSubMerchant.number_of_sales. The model sent number_of_trades,
    // a key the API does not define.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripOrderSubMerchantWithEveryProperty() {
        final OrderSubMerchant original = OrderSubMerchant.builder()
                .id("SUB12345")
                .productCategory("Electronics")
                .numberOfSales(500)
                .registrationDate(LocalDate.of(2023, 1, 15))
                .build();

        final String json = serializer.toJson(original);
        final JsonObject subMerchant = JsonParser.parseString(json).getAsJsonObject();

        assertEquals(4, subMerchant.size());
        assertEquals("SUB12345", subMerchant.get("id").getAsString());
        assertEquals("Electronics", subMerchant.get("product_category").getAsString());
        assertEquals(500, subMerchant.get("number_of_sales").getAsInt());
        assertFalse(subMerchant.has("number_of_trades"));
        assertEquals("2023-01-15", subMerchant.get("registration_date").getAsString());
        assertEquals(original, serializer.fromJson(json, OrderSubMerchant.class));
    }

    @Test
    void shouldDeserializeOrderSubMerchantSwaggerExample() {
        final String json = "{\"id\":\"SUB12345\",\"product_category\":\"Electronics\","
                + "\"number_of_sales\":500,\"registration_date\":\"2023-01-15\"}";

        final OrderSubMerchant subMerchant = serializer.fromJson(json, OrderSubMerchant.class);

        assertEquals("SUB12345", subMerchant.getId());
        assertEquals("Electronics", subMerchant.getProductCategory());
        assertEquals(Integer.valueOf(500), subMerchant.getNumberOfSales());
        assertEquals(LocalDate.of(2023, 1, 15), subMerchant.getRegistrationDate());
    }

    // ------------------------------------------------------------------------
    // PaymentSetupPaymentType
    // PaymentSetup.payment_type: the six lowercase values of the spec, on the
    // request and on both responses. The payments PaymentType sent PascalCase
    // values that the Payment Setups API rejects.
    // ------------------------------------------------------------------------

    private static final String[][] PAYMENT_SETUP_PAYMENT_TYPES = {
            {"REGULAR", "regular"},
            {"RECURRING", "recurring"},
            {"MOTO", "moto"},
            {"INSTALLMENT", "installment"},
            {"PAY_LATER", "pay_later"},
            {"UNSCHEDULED", "unscheduled"}
    };

    @Test
    void shouldWriteAndReadEveryPaymentSetupPaymentTypeAsItsSpecValue() {
        assertEquals(PAYMENT_SETUP_PAYMENT_TYPES.length, PaymentSetupPaymentType.values().length);
        for (final String[] pair : PAYMENT_SETUP_PAYMENT_TYPES) {
            final PaymentSetupPaymentType type = PaymentSetupPaymentType.valueOf(pair[0]);
            assertEquals("\"" + pair[1] + "\"", serializer.toJson(type));
            assertEquals(type, serializer.fromJson("\"" + pair[1] + "\"", PaymentSetupPaymentType.class));
        }
    }

    @Test
    void shouldSendRegularPaymentTypeByDefault() {
        final PaymentSetupsRequest request = PaymentSetupsRequest.builder()
                .processingChannelId("pc_q4dbxom5jbgudnjzjpz7j2z6uq")
                .amount(1000L)
                .currency(Currency.GBP)
                .build();

        final JsonObject json = JsonParser.parseString(serializer.toJson(request)).getAsJsonObject();

        assertEquals(PaymentSetupPaymentType.REGULAR, request.getPaymentType());
        assertEquals("regular", json.get("payment_type").getAsString());
    }

    @Test
    void shouldRoundTripEveryPaymentTypeOnRequestAndResponses() {
        for (final String[] pair : PAYMENT_SETUP_PAYMENT_TYPES) {
            final PaymentSetupPaymentType type = PaymentSetupPaymentType.valueOf(pair[0]);
            final PaymentSetupsRequest request = PaymentSetupsRequest.builder().paymentType(type).build();

            final String json = serializer.toJson(request);

            assertEquals(pair[1], JsonParser.parseString(json).getAsJsonObject().get("payment_type").getAsString());
            assertEquals(type, serializer.fromJson(json, PaymentSetupsRequest.class).getPaymentType());
            assertEquals(type, serializer.fromJson(json, PaymentSetupsResponse.class).getPaymentType());
            assertEquals(type, serializer.fromJson(json, PaymentSetupsConfirmResponse.class).getPaymentType());
        }
    }

    @Test
    void shouldNotReadPascalCasePaymentTypeTheApiDoesNotUse() {
        final PaymentSetupsResponse response =
                serializer.fromJson("{\"payment_type\":\"PayLater\"}", PaymentSetupsResponse.class);

        assertNull(response.getPaymentType());
    }

    // ------------------------------------------------------------------------
    // Order
    // PaymentSetup.order: all 10 properties, including tipping_amount and
    // surcharge_amount (int64, min 0).
    // ------------------------------------------------------------------------

    private Order createFullyPopulatedOrder() {
        return Order.builder()
                .items(Collections.singletonList(PaymentContextsItems.builder()
                        .type(PaymentContextsItemsType.DIGITAL)
                        .name("Battery Power Pack")
                        .quantity(1)
                        .unitPrice(1000)
                        .totalAmount(1000)
                        .reference("BA67A")
                        .discountAmount(150)
                        .url("https://example.com/products/my-item")
                        .imageUrl("https://example.com/products/my-item/image")
                        .build()))
                .shipping(ShippingDetails.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("jane.doe@example.com")
                        .delay(3)
                        .build())
                .subMerchants(Collections.singletonList(OrderSubMerchant.builder()
                        .id("SUB12345")
                        .productCategory("Electronics")
                        .numberOfSales(500)
                        .registrationDate(LocalDate.of(2023, 1, 15))
                        .build()))
                .discountAmount(10L)
                .invoiceId("inv_123456789")
                .shippingAmount(5L)
                .surchargeAmount(2L)
                .taxAmount(2L)
                .tippingAmount(3L)
                .amountAllocations(Collections.singletonList(PaymentSetupAmountAllocation.builder()
                        .id("ent_w4jelhppmfiufdnatam37wrfc4")
                        .amount(1000L)
                        .reference("ORD-5023-4E89")
                        .build()))
                .build();
    }

    @Test
    void shouldRoundTripOrderWithEveryProperty() {
        final Order original = createFullyPopulatedOrder();

        final String json = serializer.toJson(original);
        final JsonObject order = JsonParser.parseString(json).getAsJsonObject();

        assertEquals(10, order.size());
        assertEquals("Battery Power Pack",
                order.getAsJsonArray("items").get(0).getAsJsonObject().get("name").getAsString());
        assertEquals("Jane", order.getAsJsonObject("shipping").get("first_name").getAsString());
        assertEquals("SUB12345",
                order.getAsJsonArray("sub_merchants").get(0).getAsJsonObject().get("id").getAsString());
        assertEquals(10L, order.get("discount_amount").getAsLong());
        assertEquals("inv_123456789", order.get("invoice_id").getAsString());
        assertEquals(5L, order.get("shipping_amount").getAsLong());
        assertEquals(2L, order.get("surcharge_amount").getAsLong());
        assertEquals(2L, order.get("tax_amount").getAsLong());
        assertEquals(3L, order.get("tipping_amount").getAsLong());
        assertEquals("ent_w4jelhppmfiufdnatam37wrfc4",
                order.getAsJsonArray("amount_allocations").get(0).getAsJsonObject().get("id").getAsString());
        assertEquals(original, serializer.fromJson(json, Order.class));
    }

    @Test
    void shouldDeserializeOrderSwaggerExample() {
        final String json = "{"
                + "\"items\":[{\"name\":\"Battery Power Pack\",\"quantity\":1,\"unit_price\":1000,"
                + "\"total_amount\":1000,\"reference\":\"BA67A\",\"discount_amount\":150,"
                + "\"url\":\"https://example.com/products/my-item\","
                + "\"image_url\":\"https://example.com/products/my-item/image\",\"type\":\"digital\"}],"
                + "\"shipping\":{\"first_name\":\"Jane\",\"last_name\":\"Doe\","
                + "\"email\":\"jane.doe@example.com\",\"delay\":3},"
                + "\"sub_merchants\":[{\"id\":\"SUB12345\",\"product_category\":\"Electronics\","
                + "\"number_of_sales\":500,\"registration_date\":\"2023-01-15\"}],"
                + "\"invoice_id\":\"inv_123456789\","
                + "\"shipping_amount\":5,"
                + "\"discount_amount\":10,"
                + "\"tax_amount\":2,"
                + "\"tipping_amount\":2,"
                + "\"surcharge_amount\":2,"
                + "\"amount_allocations\":[{\"id\":\"ent_w4jelhppmfiufdnatam37wrfc4\",\"amount\":1000,"
                + "\"reference\":\"ORD-5023-4E89\"}]"
                + "}";

        final Order order = serializer.fromJson(json, Order.class);

        assertEquals(1, order.getItems().size());
        assertEquals(PaymentContextsItemsType.DIGITAL, order.getItems().get(0).getType());
        assertEquals(Integer.valueOf(1000), order.getItems().get(0).getUnitPrice());
        assertEquals("Doe", order.getShipping().getLastName());
        assertEquals(Integer.valueOf(3), order.getShipping().getDelay());
        assertEquals(LocalDate.of(2023, 1, 15), order.getSubMerchants().get(0).getRegistrationDate());
        assertEquals("inv_123456789", order.getInvoiceId());
        assertEquals(Long.valueOf(5L), order.getShippingAmount());
        assertEquals(Long.valueOf(10L), order.getDiscountAmount());
        assertEquals(Long.valueOf(2L), order.getTaxAmount());
        assertEquals(Long.valueOf(2L), order.getTippingAmount());
        assertEquals(Long.valueOf(2L), order.getSurchargeAmount());
        assertEquals(Long.valueOf(1000L), order.getAmountAllocations().get(0).getAmount());
    }

    // ------------------------------------------------------------------------
    // Tamara
    // PaymentSetup.payment_methods.tamara: status, flags, initialization and
    // risk_assessment with its 4 properties.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripTamaraWithEveryProperty() {
        final Tamara original = new Tamara();
        original.setStatus(PaymentMethodStatus.INITIALIZATION_REQUIRED);
        original.setFlags(Collections.singletonList("customer.property_required"));
        original.setInitialization(PaymentMethodInitialization.ENABLED);
        original.setRiskAssessment(TamaraRiskAssessment.builder()
                .premiumCustomer(true)
                .riskScore(42)
                .orderCountLast24h(1)
                .linkedDeviceCount(2)
                .build());

        final String json = serializer.toJson(PaymentMethods.builder().tamara(original).build());
        final JsonObject tamara = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("tamara");
        final JsonObject riskAssessment = tamara.getAsJsonObject("risk_assessment");

        assertEquals(4, tamara.size());
        assertEquals("initialization_required", tamara.get("status").getAsString());
        assertEquals("customer.property_required", tamara.getAsJsonArray("flags").get(0).getAsString());
        assertEquals("enabled", tamara.get("initialization").getAsString());
        assertEquals(4, riskAssessment.size());
        assertTrue(riskAssessment.get("premium_customer").getAsBoolean());
        assertEquals(42, riskAssessment.get("risk_score").getAsInt());
        assertEquals(1, riskAssessment.get("order_count_last24h").getAsInt());
        assertEquals(2, riskAssessment.get("linked_device_count").getAsInt());
        assertEquals(original, serializer.fromJson(json, PaymentMethods.class).getTamara());
    }

    @Test
    void shouldDeserializeTamaraSwaggerExample() {
        final String json = "{\"tamara\":{"
                + "\"status\":\"initialization_required\","
                + "\"flags\":[],"
                + "\"initialization\":\"disabled\","
                + "\"risk_assessment\":{\"premium_customer\":false,\"risk_score\":42,"
                + "\"order_count_last24h\":0,\"linked_device_count\":2}"
                + "}}";

        final Tamara tamara = serializer.fromJson(json, PaymentMethods.class).getTamara();

        assertEquals(PaymentMethodStatus.INITIALIZATION_REQUIRED, tamara.getStatus());
        assertTrue(tamara.getFlags().isEmpty());
        assertEquals(PaymentMethodInitialization.DISABLED, tamara.getInitialization());
        assertEquals(Boolean.FALSE, tamara.getRiskAssessment().getPremiumCustomer());
        assertEquals(Integer.valueOf(42), tamara.getRiskAssessment().getRiskScore());
        assertEquals(Integer.valueOf(0), tamara.getRiskAssessment().getOrderCountLast24h());
        assertEquals(Integer.valueOf(2), tamara.getRiskAssessment().getLinkedDeviceCount());
    }

    @Test
    void shouldSendDisabledInitializationByDefaultForTamara() {
        assertEquals("{\"initialization\":\"disabled\"}", serializer.toJson(new Tamara()));
    }

}
