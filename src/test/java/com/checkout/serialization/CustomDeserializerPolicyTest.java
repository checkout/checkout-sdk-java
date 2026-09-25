package com.checkout.serialization;

import com.checkout.GsonSerializer;
import com.checkout.ItemsResponse;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.common.Currency;
import com.checkout.common.CountryCode;
import com.checkout.payments.PaymentAction;
import com.checkout.payments.Passenger;
import com.checkout.payments.AirlineData;
import com.checkout.payments.ProductResponse;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    /**
     * singleOrArrayDeserializer normalizes the oneOf[array, object] shape of
     * processing.airline_data[].passenger into a list. It delegates each element to the default
     * (policy-aware) Gson, so snake_case keys map to annotation-less camelCase fields and the
     * LocalDate adapter still applies. It must never map property names itself.
     */
    @Test
    void singleOrArrayDeserializer_honorsNamingPolicyForASingleObject() {
        final String json = "{\"passenger\":{"
                + "\"first_name\":\"John\","
                + "\"last_name\":\"White\","
                + "\"date_of_birth\":\"1990-05-26\","
                + "\"address\":{\"country\":\"US\"}"
                + "}}";

        final AirlineData airline = serializer.fromJson(json, AirlineData.class);

        assertNotNull(airline.getPassenger());
        assertEquals(1, airline.getPassenger().size());
        assertEquals("John", airline.getPassenger().get(0).getFirstName());
        assertEquals("White", airline.getPassenger().get(0).getLastName());
        assertEquals(LocalDate.of(1990, 5, 26), airline.getPassenger().get(0).getDateOfBirth());
        assertEquals(CountryCode.US, airline.getPassenger().get(0).getAddress().getCountry());
    }

    @Test
    void singleOrArrayDeserializer_honorsNamingPolicyForAnArray() {
        final String json = "{\"passenger\":["
                + "{\"first_name\":\"John\",\"date_of_birth\":\"1990-05-26\"},"
                + "{\"first_name\":\"Jane\",\"date_of_birth\":\"1992-01-03\"}"
                + "]}";

        final AirlineData airline = serializer.fromJson(json, AirlineData.class);

        assertEquals(2, airline.getPassenger().size());
        assertEquals("John", airline.getPassenger().get(0).getFirstName());
        assertEquals(LocalDate.of(1990, 5, 26), airline.getPassenger().get(0).getDateOfBirth());
        assertEquals("Jane", airline.getPassenger().get(1).getFirstName());
        assertEquals(LocalDate.of(1992, 1, 3), airline.getPassenger().get(1).getDateOfBirth());
    }

    /**
     * Only a deserializer is registered, so writing goes through the reflective adapter and
     * always emits an array. Registering a serializer would silently change every outbound
     * request that carries airline data.
     */
    @Test
    void singleOrArrayDeserializer_isReadOnlySoWritesStayAnArray() {
        final String json = serializer.toJson(AirlineData.builder()
                .passenger(Collections.singletonList(
                        Passenger.builder().firstName("John").build()))
                .build());

        assertTrue(json.contains("\"passenger\":[{"), json);
        assertTrue(!json.contains("\"passenger\":{"), json);
    }

    @Test
    void singleOrArrayDeserializer_readsNullAsNull() {
        final AirlineData airline =
                serializer.fromJson("{\"passenger\":null}", AirlineData.class);

        assertNull(airline.getPassenger());
    }

}
