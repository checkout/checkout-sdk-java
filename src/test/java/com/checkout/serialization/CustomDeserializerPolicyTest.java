package com.checkout.serialization;

import com.checkout.GsonSerializer;
import com.checkout.ItemsResponse;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentAction;
import com.checkout.payments.ProductResponse;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Deserialization coverage for the custom Gson deserializers registered in {@link GsonSerializer}
 * that perform their own field/key handling. Guards that they honor the SDK's global
 * LOWER_CASE_WITH_UNDERSCORES naming policy (i.e. still work when fields have no @SerializedName).
 */
class CustomDeserializerPolicyTest {

    private final GsonSerializer serializer = new GsonSerializer();

    /**
     * getProductDeserializer matches JSON keys against @SerializedName, the exact field name, OR the
     * naming-policy translation. This verifies snake_case keys map to annotation-less camelCase fields.
     */
    @Test
    void productDeserializer_honorsNamingPolicyWithoutSerializedName() {
        final String json = "{"
                + "\"name\":\"Annual License\","
                + "\"quantity\":1,"
                + "\"unit_price\":10000,"
                + "\"commodity_code\":\"99998888\","
                + "\"unit_of_measure\":\"license\","
                + "\"total_amount\":10000,"
                + "\"tax_amount\":1000,"
                + "\"discount_amount\":500,"
                + "\"wxpay_goods_id\":\"WX-1\","
                + "\"image_url\":\"https://example.com/x.png\","
                + "\"service_ends_on\":\"2025-01-01\""
                + "}";

        final ProductResponse product = serializer.fromJson(json, ProductResponse.class);

        assertNotNull(product);
        assertEquals("Annual License", product.getName());
        assertEquals(10000L, product.getUnitPrice());
        assertEquals("99998888", product.getCommodityCode());
        assertEquals("license", product.getUnitOfMeasure());
        assertEquals(10000L, product.getTotalAmount());
        assertEquals(1000L, product.getTaxAmount());
        assertEquals(500L, product.getDiscountAmount());
        assertEquals("WX-1", product.getWxpayGoodsId());
        assertEquals("https://example.com/x.png", product.getImageUrl());
        assertEquals(LocalDate.of(2025, 1, 1), product.getServiceEndsOn());
    }

    /**
     * getScheduleResponseDeserializer builds a currency-keyed map and delegates each value to the
     * default (policy-aware) Gson; it also maps the HAL _links block.
     */
    @Test
    void scheduleDeserializer_deserializesCurrencyScheduleAndLinks() {
        final String json = "{"
                + "\"USD\":{\"enabled\":true,\"threshold\":100},"
                + "\"_links\":{\"self\":{\"href\":\"https://api.checkout.com/schedule\"}}"
                + "}";

        final GetScheduleResponse response = serializer.fromJson(json, GetScheduleResponse.class);

        assertNotNull(response);
        assertNotNull(response.getCurrency());
        assertTrue(response.getCurrency().containsKey(Currency.USD));
        assertEquals(true, response.getCurrency().get(Currency.USD).getEnabled());
        assertEquals(100, response.getCurrency().get(Currency.USD).getThreshold());
        assertNotNull(response.getLinks());
        assertEquals("https://api.checkout.com/schedule", response.getLinks().get("self").getHref());
    }

    /**
     * paymentActionsResponseDeserializer extracts the JSON array and delegates each element to the
     * default (policy-aware) Gson, so snake_case fields map to annotation-less camelCase fields.
     */
    @Test
    void paymentActionsDeserializer_deserializesArrayWithPolicyMappedFields() {
        final Type type = new TypeToken<ItemsResponse<PaymentAction>>() {
        }.getType();
        final String json = "["
                + "{\"id\":\"act_1\",\"amount\":1000,\"approved\":true,\"auth_code\":\"AC1\","
                + "\"response_summary\":\"Approved\",\"reference\":\"ref-1\"},"
                + "{\"id\":\"act_2\",\"amount\":2000,\"approved\":false,\"auth_code\":\"AC2\","
                + "\"response_summary\":\"Declined\",\"reference\":\"ref-2\"}"
                + "]";

        final ItemsResponse<PaymentAction> response = serializer.fromJson(json, type);

        assertNotNull(response);
        assertEquals(2, response.getItems().size());
        final PaymentAction first = response.getItems().get(0);
        assertEquals("act_1", first.getId());
        assertEquals(1000L, first.getAmount());
        assertEquals(true, first.getApproved());
        assertEquals("AC1", first.getAuthCode());
        assertEquals("Approved", first.getResponseSummary());
        assertEquals("ref-1", first.getReference());
    }
}
