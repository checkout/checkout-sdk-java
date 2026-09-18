package com.checkout.inventory.response;

import com.checkout.GsonSerializer;
import com.checkout.inventory.InventoryHalLink;
import com.checkout.inventory.InventoryLevelsSource;
import com.checkout.inventory.InventoryLevelsState;
import com.checkout.inventory.InventoryMoney;
import com.checkout.inventory.InventoryProductCondition;
import com.checkout.inventory.InventoryReservationItem;
import com.checkout.inventory.InventoryReservationState;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Schema validation tests for the Inventory response classes: InventoryLevels,
 * InventoryReservation and InventoryProductKnowledge. Fields verified against
 * shared/swagger.json.
 */
class InventoryResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------------
    // InventoryLevels - covers state/source enums, embedded product and _links.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeInventoryLevelsSwaggerExample() {
        final String swaggerJson = "{"
                + "\"variant_id\":\"var_123\","
                + "\"on_hand\":10,"
                + "\"reserved\":2,"
                + "\"safety_stock\":1,"
                + "\"available\":7,"
                + "\"state\":\"in_stock\","
                + "\"source\":\"managed\","
                + "\"created_on\":\"2026-07-01T09:15:00Z\","
                + "\"modified_on\":\"2026-07-13T14:02:11Z\","
                + "\"_links\":{\"self\":{\"href\":\"https://api.checkout.com/inventory/var_123\",\"actions\":[\"GET\"]},"
                + "\"set\":{\"href\":\"https://api.checkout.com/inventory/var_123\",\"actions\":[\"PUT\"]}}"
                + "}";

        final InventoryLevels levels = serializer.fromJson(swaggerJson, InventoryLevels.class);

        assertNotNull(levels);
        assertEquals("var_123", levels.getVariantId());
        assertEquals(10, levels.getOnHand());
        assertEquals(7, levels.getAvailable());
        assertEquals(InventoryLevelsState.IN_STOCK, levels.getState());
        assertEquals(InventoryLevelsSource.MANAGED, levels.getSource());
        assertEquals(Instant.parse("2026-07-01T09:15:00Z"), levels.getCreatedOn());
        assertNotNull(levels.getLinks());
        assertEquals("https://api.checkout.com/inventory/var_123", levels.getLinks().getSelf().getHref());
        assertNull(levels.getProduct());
    }

    @Test
    void shouldDeserializeInventoryLevelsWithEmbeddedProduct() {
        final String swaggerJson = "{"
                + "\"variant_id\":\"var_123\","
                + "\"state\":\"limited\","
                + "\"product\":{"
                + "\"variant_id\":\"var_123\",\"title\":\"Belt\",\"description\":\"desc\","
                + "\"product_url\":\"https://example.com\",\"image_url\":\"https://example.com/i.jpg\","
                + "\"condition\":\"new\",\"created_on\":\"2026-08-01T09:15:00Z\",\"modified_on\":\"2026-08-13T14:02:11Z\","
                + "\"_links\":{\"self\":{\"href\":\"https://example.com/self\"}}"
                + "}"
                + "}";

        final InventoryLevels levels = serializer.fromJson(swaggerJson, InventoryLevels.class);

        assertNotNull(levels.getProduct());
        assertEquals("Belt", levels.getProduct().getTitle());
        assertEquals(InventoryProductCondition.NEW, levels.getProduct().getCondition());
    }

    @Test
    void shouldRoundTripInventoryLevels() {
        final InventoryLevels original = new InventoryLevels();
        original.setVariantId("var_123");
        original.setOnHand(10);
        original.setReserved(2);
        original.setSafetyStock(1);
        original.setAvailable(7);
        original.setState(InventoryLevelsState.OUT_OF_STOCK);
        original.setSource(InventoryLevelsSource.SYNC);
        original.setCreatedOn(Instant.parse("2026-07-01T09:15:00Z"));
        original.setModifiedOn(Instant.parse("2026-07-13T14:02:11Z"));
        final InventoryLevelsLinks links = new InventoryLevelsLinks();
        final InventoryHalLink self = new InventoryHalLink();
        self.setHref("https://api.checkout.com/inventory/var_123");
        links.setSelf(self);
        original.setLinks(links);

        final String json = serializer.toJson(original);
        final InventoryLevels deserialized = serializer.fromJson(json, InventoryLevels.class);

        assertEquals(original.getVariantId(), deserialized.getVariantId());
        assertEquals(original.getState(), deserialized.getState());
        assertEquals(original.getSource(), deserialized.getSource());
        assertEquals(original.getLinks().getSelf().getHref(), deserialized.getLinks().getSelf().getHref());
    }

    // ------------------------------------------------------------------------
    // InventoryReservation - covers state enum, items and the three-way _links.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeInventoryReservationSwaggerExample() {
        final String swaggerJson = "{"
                + "\"id\":\"rsv_tkoi5db4hryu5cei5vwoabr7we\","
                + "\"state\":\"held\","
                + "\"owner_type\":\"ucp_session\","
                + "\"owner_reference\":\"cs_8f42\","
                + "\"items\":[{\"variant_id\":\"var_123\",\"quantity\":2}],"
                + "\"expires_at\":\"2026-07-14T08:47:00Z\","
                + "\"created_on\":\"2026-07-14T08:32:00Z\","
                + "\"_links\":{\"self\":{\"href\":\"https://example.com/self\"},"
                + "\"commit\":{\"href\":\"https://example.com/commit\"},"
                + "\"release\":{\"href\":\"https://example.com/release\"}}"
                + "}";

        final InventoryReservation reservation = serializer.fromJson(swaggerJson, InventoryReservation.class);

        assertNotNull(reservation);
        assertEquals("rsv_tkoi5db4hryu5cei5vwoabr7we", reservation.getId());
        assertEquals(InventoryReservationState.HELD, reservation.getState());
        assertEquals(1, reservation.getItems().size());
        assertEquals("var_123", reservation.getItems().get(0).getVariantId());
        assertNotNull(reservation.getLinks().getCommit());
        assertNotNull(reservation.getLinks().getRelease());
    }

    @Test
    void shouldRoundTripInventoryReservation() {
        final InventoryReservation original = new InventoryReservation();
        original.setId("rsv_tkoi5db4hryu5cei5vwoabr7we");
        original.setState(InventoryReservationState.COMMITTED);
        original.setOwnerType("ucp_session");
        original.setOwnerReference("cs_8f42");
        original.setItems(List.of(InventoryReservationItem.builder().variantId("var_123").quantity(2).build()));
        original.setExpiresAt(Instant.parse("2026-07-14T08:47:00Z"));
        original.setCreatedOn(Instant.parse("2026-07-14T08:32:00Z"));
        final InventoryReservationLinks links = new InventoryReservationLinks();
        final InventoryHalLink self = new InventoryHalLink();
        self.setHref("https://example.com/self");
        links.setSelf(self);
        original.setLinks(links);

        final String json = serializer.toJson(original);
        final InventoryReservation deserialized = serializer.fromJson(json, InventoryReservation.class);

        assertEquals(original.getId(), deserialized.getId());
        assertEquals(original.getState(), deserialized.getState());
        assertEquals(original.getItems(), deserialized.getItems());
    }

    // ------------------------------------------------------------------------
    // InventoryProductKnowledge - covers all fields, including nested InventoryMoney,
    // condition enum and _links (self/set/delete).
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeInventoryProductKnowledgeSwaggerExample() {
        final String swaggerJson = "{"
                + "\"variant_id\":\"var_123\",\"title\":\"Classic leather belt, brown\","
                + "\"description\":\"A full-grain leather belt with a brushed nickel buckle.\","
                + "\"product_url\":\"https://merchant.example.com/products/classic-leather-belt-brown\","
                + "\"image_url\":\"https://merchant.example.com/images/belt-brown-main.jpg\","
                + "\"model_3d_url\":\"https://merchant.example.com/models/belt-brown.glb\","
                + "\"price\":{\"amount\":1999,\"currency\":\"USD\"},"
                + "\"sale_price\":{\"amount\":1499,\"currency\":\"USD\"},"
                + "\"condition\":\"new\","
                + "\"created_on\":\"2026-08-01T09:15:00Z\",\"modified_on\":\"2026-08-13T14:02:11Z\","
                + "\"_links\":{\"self\":{\"href\":\"https://example.com/self\"},"
                + "\"set\":{\"href\":\"https://example.com/set\"},"
                + "\"delete\":{\"href\":\"https://example.com/delete\"}}"
                + "}";

        final InventoryProductKnowledge product = serializer.fromJson(swaggerJson, InventoryProductKnowledge.class);

        assertNotNull(product);
        assertEquals("var_123", product.getVariantId());
        assertEquals("https://merchant.example.com/models/belt-brown.glb", product.getModel3dUrl());
        assertEquals(1999L, product.getPrice().getAmount());
        assertEquals("USD", product.getPrice().getCurrency());
        assertEquals(InventoryProductCondition.NEW, product.getCondition());
        assertNotNull(product.getLinks().getDelete());
    }

    @Test
    void shouldRoundTripInventoryProductKnowledgeWithAllOptionalFields() {
        final InventoryProductKnowledge original = new InventoryProductKnowledge();
        original.setVariantId("var_123");
        original.setTitle("Classic leather belt, brown");
        original.setDescription("A full-grain leather belt with a brushed nickel buckle.");
        original.setProductUrl("https://merchant.example.com/products/classic-leather-belt-brown");
        original.setImageUrl("https://merchant.example.com/images/belt-brown-main.jpg");
        original.setAdditionalImageUrls(List.of("https://merchant.example.com/images/belt-brown-alt1.jpg"));
        original.setVideoUrl("https://merchant.example.com/videos/belt-brown.mp4");
        original.setModel3dUrl("https://merchant.example.com/models/belt-brown.glb");
        original.setSku("BELT-BRN-001");
        original.setGtin("00012345678905");
        original.setMpn("MPN-4471");
        original.setBrand("Acme Leathercraft");
        original.setCategory("Apparel & Accessories > Belts");
        original.setPrice(InventoryMoney.builder().amount(1999L).currency("USD").build());
        original.setSalePrice(InventoryMoney.builder().amount(1499L).currency("USD").build());
        original.setSalePriceStartsAt(Instant.parse("2026-08-01T00:00:00Z"));
        original.setSalePriceEndsAt(Instant.parse("2026-08-31T23:59:59Z"));
        original.setGroupId("grp_belt_classic");
        original.setGroupTitle("Classic leather belt");
        original.setColor("Brown");
        original.setSize("M");
        original.setSizeSystem("US");
        original.setGender("unisex");
        original.setCondition(InventoryProductCondition.USED);
        original.setMaterial("Full-grain leather");
        original.setAgeGroup("adult");
        original.setLength(110d);
        original.setWidth(3.5);
        original.setHeight(0.5);
        original.setDimensionUnit("cm");
        original.setWeight(0.2);
        original.setWeightUnit("kg");
        original.setExpirationDate(Instant.parse("2027-01-01T00:00:00Z"));
        original.setHarmonizedSystemCode("4203.30");
        original.setCountryOfOrigin("IT");
        original.setSellerName("Acme Leathercraft Ltd");
        original.setSellerUrl("https://acme-leathercraft.example.com");
        original.setSellerPrivacyPolicy("https://acme-leathercraft.example.com/privacy");
        original.setSellerTos("https://acme-leathercraft.example.com/terms");
        final InventoryProductLinks links = new InventoryProductLinks();
        final InventoryHalLink self = new InventoryHalLink();
        self.setHref("https://example.com/self");
        links.setSelf(self);
        original.setLinks(links);

        final String json = serializer.toJson(original);
        final InventoryProductKnowledge deserialized = serializer.fromJson(json, InventoryProductKnowledge.class);

        assertEquals(original.getVariantId(), deserialized.getVariantId());
        assertEquals(original.getModel3dUrl(), deserialized.getModel3dUrl());
        assertEquals(original.getPrice(), deserialized.getPrice());
        assertEquals(original.getCondition(), deserialized.getCondition());
        assertEquals(original.getSalePriceStartsAt(), deserialized.getSalePriceStartsAt());
    }

}
