package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank.PayByBank;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank.PayByBankAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank.PayByBankBank;
import com.checkout.handlepaymentsandpayouts.setups.entities.risk.PaymentSetupRisk;
import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Wire format tests for the Payment Setup {@code payment_methods.paybybank} key and the {@code risk} object.
 */
class PaymentSetupsPayByBankSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializePayByBankWithLiteralPaybybankKey() {
        final PayByBank payByBank = new PayByBank();
        payByBank.setBankId("bank_123");
        final PaymentSetupsRequest request = PaymentSetupsRequest.builder()
                .processingChannelId("pc_q4dbxom5jbgudnjzjpz7j2z6uq")
                .amount(1000L)
                .currency(Currency.GBP)
                .paymentMethods(PaymentMethods.builder().payByBank(payByBank).build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"paybybank\""));
        assertFalse(json.contains("pay_by_bank"));
        final JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        assertEquals("bank_123", root.getAsJsonObject("payment_methods")
                .getAsJsonObject("paybybank").get("bank_id").getAsString());
    }

    @Test
    void shouldDeserializePayByBankActionFromResponse() {
        final String json = "{"
                + "\"id\":\"ps_123\","
                + "\"payment_methods\":{\"paybybank\":{"
                + "\"status\":\"action_required\","
                + "\"flags\":[\"flag\"],"
                + "\"bank_id\":\"bank_123\","
                + "\"action\":{\"type\":\"select_bank\",\"banks\":[{"
                + "\"bank_id\":\"bank_123\",\"display_name\":\"Example Bank\","
                + "\"logo_url\":\"https://example.com/logo.png\",\"available\":true}]}"
                + "}},"
                + "\"risk\":{\"enabled\":false}"
                + "}";

        final PaymentSetupsResponse response = serializer.fromJson(json, PaymentSetupsResponse.class);

        final PayByBank payByBank = response.getPaymentMethods().getPayByBank();
        assertNotNull(payByBank);
        assertEquals(Collections.singletonList("flag"), payByBank.getFlags());
        assertEquals("bank_123", payByBank.getBankId());
        final PayByBankAction action = payByBank.getAction();
        assertNotNull(action);
        assertEquals("select_bank", action.getType());
        final PayByBankBank bank = action.getBanks().get(0);
        assertEquals("bank_123", bank.getBankId());
        assertEquals("Example Bank", bank.getDisplayName());
        assertEquals("https://example.com/logo.png", bank.getLogoUrl());
        assertTrue(bank.getAvailable());
        assertFalse(response.getRisk().getEnabled());
    }

    @Test
    void shouldSerializeAndRoundTripRiskEnabled() {
        final PaymentSetupsRequest request = PaymentSetupsRequest.builder()
                .risk(PaymentSetupRisk.builder().enabled(true).build())
                .build();

        final String json = serializer.toJson(request);
        final JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        assertTrue(root.getAsJsonObject("risk").get("enabled").getAsBoolean());

        final PaymentSetupsRequest restored = serializer.fromJson(json, PaymentSetupsRequest.class);
        assertEquals(request.getRisk(), restored.getRisk());
    }

    @Test
    void shouldDeserializeRiskSwaggerExample() {
        final PaymentSetupRisk risk = serializer.fromJson("{\"enabled\":true}", PaymentSetupRisk.class);
        assertTrue(risk.getEnabled());
    }
}
