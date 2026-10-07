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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        assertTrue(methodsJson.contains("\"pay_by_bank\""));
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
}
