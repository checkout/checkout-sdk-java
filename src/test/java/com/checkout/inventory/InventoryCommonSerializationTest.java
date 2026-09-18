package com.checkout.inventory;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for the shared Inventory types: InventoryHalLink, InventoryMoney and
 * InventoryReservationItem. Fields verified against shared/swagger.json.
 */
class InventoryCommonSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------------
    // InventoryHalLink
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripInventoryHalLink() {
        final InventoryHalLink original = new InventoryHalLink();
        original.setHref("https://api.checkout.com/inventory/var_123");
        original.setActions(List.of("GET"));
        original.setTypes(List.of("application/json"));

        final String json = serializer.toJson(original);
        final InventoryHalLink deserialized = serializer.fromJson(json, InventoryHalLink.class);

        assertEquals(original, deserialized);
        assertEquals("https://api.checkout.com/inventory/var_123", deserialized.getHref());
        assertEquals(List.of("GET"), deserialized.getActions());
        assertEquals(List.of("application/json"), deserialized.getTypes());
    }

    @Test
    void shouldDeserializeInventoryHalLinkSwaggerExample() {
        final String swaggerJson = "{"
                + "\"href\": \"https://api.checkout.com/inventory/var_123\","
                + "\"actions\": [\"GET\"],"
                + "\"types\": [\"application/json\"]"
                + "}";

        final InventoryHalLink link = serializer.fromJson(swaggerJson, InventoryHalLink.class);

        assertNotNull(link);
        assertEquals("https://api.checkout.com/inventory/var_123", link.getHref());
    }

    // ------------------------------------------------------------------------
    // InventoryMoney
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeInventoryMoneyWithRequiredFields() {
        final InventoryMoney money = InventoryMoney.builder()
                .amount(1999L)
                .currency("USD")
                .build();

        final String json = serializer.toJson(money);

        assertNotNull(json);
        assertTrue(json.contains("\"amount\":1999"));
        assertTrue(json.contains("\"currency\":\"USD\""));
    }

    @Test
    void shouldRoundTripInventoryMoney() {
        final InventoryMoney original = InventoryMoney.builder()
                .amount(1999L)
                .currency("USD")
                .build();

        final String json = serializer.toJson(original);
        final InventoryMoney deserialized = serializer.fromJson(json, InventoryMoney.class);

        assertEquals(original, deserialized);
    }

    // ------------------------------------------------------------------------
    // InventoryReservationItem
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeInventoryReservationItemWithRequiredFields() {
        final InventoryReservationItem item = InventoryReservationItem.builder()
                .variantId("var_123")
                .quantity(2)
                .build();

        final String json = serializer.toJson(item);

        assertNotNull(json);
        assertTrue(json.contains("\"variant_id\":\"var_123\""));
        assertTrue(json.contains("\"quantity\":2"));
    }

    @Test
    void shouldRoundTripInventoryReservationItem() {
        final InventoryReservationItem original = InventoryReservationItem.builder()
                .variantId("var_123")
                .quantity(2)
                .build();

        final String json = serializer.toJson(original);
        final InventoryReservationItem deserialized = serializer.fromJson(json, InventoryReservationItem.class);

        assertEquals(original, deserialized);
    }

    // ------------------------------------------------------------------------
    // Enums: InventoryLevelsState, InventoryLevelsSource, InventoryReservationState,
    // InventoryProductCondition
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeInventoryLevelsStateAsSnakeCase() {
        assertEquals("\"limited\"", serializer.toJson(InventoryLevelsState.LIMITED));
        assertEquals("\"out_of_stock\"", serializer.toJson(InventoryLevelsState.OUT_OF_STOCK));
    }

    @Test
    void shouldSerializeInventoryLevelsSourceAsSnakeCase() {
        assertEquals("\"managed\"", serializer.toJson(InventoryLevelsSource.MANAGED));
        assertEquals("\"sync\"", serializer.toJson(InventoryLevelsSource.SYNC));
    }

    @Test
    void shouldSerializeInventoryReservationStateAsSnakeCase() {
        assertEquals("\"held\"", serializer.toJson(InventoryReservationState.HELD));
        assertEquals("\"expired\"", serializer.toJson(InventoryReservationState.EXPIRED));
    }

    @Test
    void shouldSerializeInventoryProductConditionAsLowerCase() {
        assertEquals("\"new\"", serializer.toJson(InventoryProductCondition.NEW));
        assertEquals("\"refurbished\"", serializer.toJson(InventoryProductCondition.REFURBISHED));
    }

}
