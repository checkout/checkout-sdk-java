package com.checkout.inventory.request;

import com.checkout.GsonSerializer;
import com.checkout.inventory.InventoryMoney;
import com.checkout.inventory.InventoryProductCondition;
import com.checkout.inventory.InventoryReservationItem;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for the Inventory request classes: InventoryAdjustmentRequest,
 * InventoryReservationRequest, InventorySetLevelsRequest, InventorySetProductRequest and
 * InventoryLevelsQueryFilter. Fields verified against shared/swagger.json.
 */
class InventoryRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------------
    // InventoryAdjustmentRequest
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeInventoryAdjustmentRequest() {
        final InventoryAdjustmentRequest request = InventoryAdjustmentRequest.builder()
                .variantId("var_123")
                .delta(-3)
                .reason("damaged in warehouse")
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"variant_id\":\"var_123\""));
        assertTrue(json.contains("\"delta\":-3"));
        assertTrue(json.contains("\"reason\":\"damaged in warehouse\""));
    }

    @Test
    void shouldRoundTripInventoryAdjustmentRequest() {
        final InventoryAdjustmentRequest original = InventoryAdjustmentRequest.builder()
                .variantId("var_123")
                .delta(5)
                .reason("found stock")
                .build();

        final String json = serializer.toJson(original);
        final InventoryAdjustmentRequest deserialized = serializer.fromJson(json, InventoryAdjustmentRequest.class);

        assertEquals(original, deserialized);
    }

    // ------------------------------------------------------------------------
    // InventoryReservationRequest
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeInventoryReservationRequestWithRequiredFields() {
        final InventoryReservationRequest request = InventoryReservationRequest.builder()
                .ownerType("ucp_session")
                .ownerReference("cs_8f42")
                .items(List.of(InventoryReservationItem.builder().variantId("var_123").quantity(2).build()))
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"owner_type\":\"ucp_session\""));
        assertTrue(json.contains("\"owner_reference\":\"cs_8f42\""));
        assertTrue(json.contains("\"items\""));
        assertFalse(json.contains("ttl_seconds"));
    }

    @Test
    void shouldSerializeInventoryReservationRequestWithAllFields() {
        final InventoryReservationRequest request = InventoryReservationRequest.builder()
                .ownerType("ucp_session")
                .ownerReference("cs_8f42")
                .items(List.of(InventoryReservationItem.builder().variantId("var_123").quantity(2).build()))
                .ttlSeconds(900)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"ttl_seconds\":900"));
    }

    @Test
    void shouldRoundTripInventoryReservationRequest() {
        final InventoryReservationRequest original = InventoryReservationRequest.builder()
                .ownerType("ucp_session")
                .ownerReference("cs_8f42")
                .items(List.of(InventoryReservationItem.builder().variantId("var_123").quantity(2).build()))
                .ttlSeconds(900)
                .build();

        final String json = serializer.toJson(original);
        final InventoryReservationRequest deserialized = serializer.fromJson(json, InventoryReservationRequest.class);

        assertEquals(original, deserialized);
    }

    // ------------------------------------------------------------------------
    // InventorySetLevelsRequest
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeInventorySetLevelsRequestWithRequiredFields() {
        final InventorySetLevelsRequest request = InventorySetLevelsRequest.builder()
                .onHand(25)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"on_hand\":25"));
        assertFalse(json.contains("safety_stock"));
        assertFalse(json.contains("reason"));
    }

    @Test
    void shouldSerializeInventorySetLevelsRequestWithAllFields() {
        final InventorySetLevelsRequest request = InventorySetLevelsRequest.builder()
                .onHand(25)
                .safetyStock(2)
                .reason("stock take 2026-07")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"on_hand\":25"));
        assertTrue(json.contains("\"safety_stock\":2"));
        assertTrue(json.contains("\"reason\":\"stock take 2026-07\""));
    }

    @Test
    void shouldRoundTripInventorySetLevelsRequest() {
        final InventorySetLevelsRequest original = InventorySetLevelsRequest.builder()
                .onHand(25)
                .safetyStock(2)
                .reason("stock take 2026-07")
                .build();

        final String json = serializer.toJson(original);
        final InventorySetLevelsRequest deserialized = serializer.fromJson(json, InventorySetLevelsRequest.class);

        assertEquals(original, deserialized);
    }

    // ------------------------------------------------------------------------
    // InventorySetProductRequest - covers all fields, including nested InventoryMoney and the
    // model_3d_url snake_case override.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeInventorySetProductRequestWithRequiredFields() {
        final InventorySetProductRequest request = InventorySetProductRequest.builder()
                .title("Classic leather belt, brown")
                .description("A full-grain leather belt with a brushed nickel buckle.")
                .productUrl("https://merchant.example.com/products/classic-leather-belt-brown")
                .imageUrl("https://merchant.example.com/images/belt-brown-main.jpg")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"title\":\"Classic leather belt, brown\""));
        assertTrue(json.contains("\"description\""));
        assertTrue(json.contains("\"product_url\""));
        assertTrue(json.contains("\"image_url\""));
        assertFalse(json.contains("\"condition\""));
    }

    @Test
    void shouldSerializeInventorySetProductRequestWithAllFields() {
        final InventorySetProductRequest request = InventorySetProductRequest.builder()
                .title("Classic leather belt, brown")
                .description("A full-grain leather belt with a brushed nickel buckle.")
                .productUrl("https://merchant.example.com/products/classic-leather-belt-brown")
                .imageUrl("https://merchant.example.com/images/belt-brown-main.jpg")
                .additionalImageUrls(List.of("https://merchant.example.com/images/belt-brown-alt1.jpg"))
                .videoUrl("https://merchant.example.com/videos/belt-brown.mp4")
                .model3dUrl("https://merchant.example.com/models/belt-brown.glb")
                .sku("BELT-BRN-001")
                .gtin("00012345678905")
                .mpn("MPN-4471")
                .brand("Acme Leathercraft")
                .category("Apparel & Accessories > Belts")
                .price(InventoryMoney.builder().amount(1999L).currency("USD").build())
                .salePrice(InventoryMoney.builder().amount(1499L).currency("USD").build())
                .salePriceStartsAt(Instant.parse("2026-08-01T00:00:00Z"))
                .salePriceEndsAt(Instant.parse("2026-08-31T23:59:59Z"))
                .groupId("grp_belt_classic")
                .groupTitle("Classic leather belt")
                .color("Brown")
                .size("M")
                .sizeSystem("US")
                .gender("unisex")
                .condition(InventoryProductCondition.NEW)
                .material("Full-grain leather")
                .ageGroup("adult")
                .length(110d)
                .width(3.5)
                .height(0.5)
                .dimensionUnit("cm")
                .weight(0.2)
                .weightUnit("kg")
                .expirationDate(Instant.parse("2027-01-01T00:00:00Z"))
                .harmonizedSystemCode("4203.30")
                .countryOfOrigin("IT")
                .sellerName("Acme Leathercraft Ltd")
                .sellerUrl("https://acme-leathercraft.example.com")
                .sellerPrivacyPolicy("https://acme-leathercraft.example.com/privacy")
                .sellerTos("https://acme-leathercraft.example.com/terms")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"model_3d_url\":\"https://merchant.example.com/models/belt-brown.glb\""));
        assertTrue(json.contains("\"condition\":\"new\""));
        assertTrue(json.contains("\"price\""));
        assertTrue(json.contains("\"sale_price\""));
        assertTrue(json.contains("\"sale_price_starts_at\""));
        assertTrue(json.contains("\"group_id\":\"grp_belt_classic\""));
    }

    @Test
    void shouldRoundTripInventorySetProductRequest() {
        final InventorySetProductRequest original = InventorySetProductRequest.builder()
                .title("Classic leather belt, brown")
                .description("A full-grain leather belt with a brushed nickel buckle.")
                .productUrl("https://merchant.example.com/products/classic-leather-belt-brown")
                .imageUrl("https://merchant.example.com/images/belt-brown-main.jpg")
                .model3dUrl("https://merchant.example.com/models/belt-brown.glb")
                .price(InventoryMoney.builder().amount(1999L).currency("USD").build())
                .condition(InventoryProductCondition.NEW)
                .build();

        final String json = serializer.toJson(original);
        final InventorySetProductRequest deserialized = serializer.fromJson(json, InventorySetProductRequest.class);

        assertEquals(original, deserialized);
        assertEquals("https://merchant.example.com/models/belt-brown.glb", deserialized.getModel3dUrl());
        assertEquals(InventoryProductCondition.NEW, deserialized.getCondition());
    }

    // ------------------------------------------------------------------------
    // InventoryLevelsQueryFilter
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeInventoryLevelsQueryFilterWithExpand() {
        final InventoryLevelsQueryFilter filter = InventoryLevelsQueryFilter.builder()
                .expand("product")
                .build();

        final String json = serializer.toJson(filter);

        assertTrue(json.contains("\"expand\":\"product\""));
    }

}
