package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.billingDescriptor.PaymentSetupBillingDescriptor;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.AmountAllocationCommission;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.PaymentSetupAmountAllocation;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.Bacs;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.BacsAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs.BacsAccountHolderType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cardpresent.CardPresent;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cardpresent.CardPresentPin;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.KlarnaAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank.PayByBank;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stablecoin.Stablecoin;
import com.checkout.handlepaymentsandpayouts.setups.entities.presentmentDetails.PaymentSetupPresentmentDetails;
import com.checkout.handlepaymentsandpayouts.setups.entities.terminal.PaymentSetupTerminal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
