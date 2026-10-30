package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.Customer;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentType.PaymentSetupPaymentType;
import com.checkout.handlepaymentsandpayouts.setups.entities.risk.PaymentSetupRisk;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentSetupsConfirmResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeConfirmResponseWithSetupsEntitiesModels() {
        final String json = "{"
                + "\"id\":\"psu_y3oqhf46pyzuxjbcn2giaqnb44\","
                + "\"processing_channel_id\":\"pc_q4dbxom5jbgudnjzjpz7j2z6uq\","
                + "\"amount\":10000,"
                + "\"currency\":\"GBP\","
                + "\"payment_type\":\"regular\","
                + "\"reference\":\"REF-0987-475\","
                + "\"risk\":{\"enabled\":false},"
                + "\"customer\":{"
                + "\"name\":\"John Smith\","
                + "\"email\":{\"address\":\"johnsmith@example.com\",\"verified\":true}"
                + "}"
                + "}";

        final PaymentSetupsConfirmResponse response =
                serializer.fromJson(json, PaymentSetupsConfirmResponse.class);

        assertNotNull(response);
        assertEquals("psu_y3oqhf46pyzuxjbcn2giaqnb44", response.getId());
        assertEquals(10000L, response.getAmount());
        assertEquals(Currency.GBP, response.getCurrency());
        assertEquals(PaymentSetupPaymentType.REGULAR, response.getPaymentType());
        assertEquals(Boolean.FALSE, response.getRisk().getEnabled());

        // The customer must be the setups.entities model, whose email is a nested object
        // (postpayments Customer models email as a plain string and would fail to deserialize this shape).
        final Customer customer = response.getCustomer();
        assertNotNull(customer);
        assertEquals("John Smith", customer.getName());
        assertNotNull(customer.getEmail());
        assertEquals("johnsmith@example.com", customer.getEmail().getAddress());
        assertEquals(true, customer.getEmail().getVerified());
    }

    @Test
    void shouldRoundTripConfirmResponseRisk() {
        final PaymentSetupsConfirmResponse original = PaymentSetupsConfirmResponse.builder()
                .id("psu_y3oqhf46pyzuxjbcn2giaqnb44")
                .paymentType(PaymentSetupPaymentType.RECURRING)
                .risk(PaymentSetupRisk.builder().enabled(true).build())
                .build();

        final String json = serializer.toJson(original);
        final JsonObject body = JsonParser.parseString(json).getAsJsonObject();

        assertTrue(json.contains("\"risk\":{\"enabled\":true}"));
        assertEquals("recurring", body.get("payment_type").getAsString());
        final PaymentSetupsConfirmResponse restored = serializer.fromJson(json, PaymentSetupsConfirmResponse.class);
        assertEquals(original.getRisk(), restored.getRisk());
        assertEquals(original.getPaymentType(), restored.getPaymentType());
        assertEquals(original.getId(), restored.getId());
    }
}
