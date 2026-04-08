package com.checkout.issuing.newfields;

import com.checkout.GsonSerializer;
import com.checkout.issuing.cards.requests.create.CardLifetime;
import com.checkout.issuing.cards.requests.create.LifetimeUnit;
import com.checkout.issuing.cards.requests.create.PhysicalCardRequest;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Schema / serialization tests for the new fields added to
 * {@link com.checkout.issuing.cards.requests.create.CardRequest}:
 * <ul>
 *   <li>{@code metadata} (udf1–udf5) — available on card creation, not just update</li>
 *   <li>{@code revocation_date} — automatic revocation date (yyyy-MM-dd)</li>
 * </ul>
 */
class IssuingCardNewFieldsSchemaTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ─── VirtualCardRequest ─────────────────────────────────────────────────

    @Test
    void shouldSerializeVirtualCardRequestWithMetadata() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId("crh_d3ozhf43pcq2xbldn2g45qnb44")
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .metadata(IssuingCardMetadata.builder()
                        .udf1("customer_tier_gold")
                        .udf2("campaign_2026_q1")
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"metadata\""), "Should contain metadata field");
        assertTrue(json.contains("\"udf1\""), "Should contain udf1");
        assertTrue(json.contains("\"customer_tier_gold\""), "Should contain udf1 value");
        assertTrue(json.contains("\"udf2\""), "Should contain udf2");
        assertTrue(json.contains("\"campaign_2026_q1\""), "Should contain udf2 value");
    }

    @Test
    void shouldSerializeVirtualCardRequestWithRevocationDate() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId("crh_d3ozhf43pcq2xbldn2g45qnb44")
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .revocationDate(LocalDate.of(2027, 3, 12))
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"revocation_date\""), "Should contain revocation_date");
        assertTrue(json.contains("2027-03-12"), "Should contain revocation date value");
    }

    @Test
    void shouldSerializeVirtualCardRequestWithBothNewFields() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId("crh_d3ozhf43pcq2xbldn2g45qnb44")
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .metadata(IssuingCardMetadata.builder()
                        .udf1("value1")
                        .udf3("value3")
                        .udf5("value5")
                        .build())
                .revocationDate(LocalDate.of(2028, 6, 30))
                .build();

        assertDoesNotThrow(() -> {
            final String json = serializer.toJson(request);
            assertNotNull(json);
            assertTrue(json.contains("\"metadata\""));
            assertTrue(json.contains("\"revocation_date\""));
            assertTrue(json.contains("\"udf1\""));
            assertTrue(json.contains("\"udf5\""));
            assertTrue(json.contains("2028-06-30"));
        });
    }

    @Test
    void shouldNotIncludeNullMetadataInSerialization() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId("crh_d3ozhf43pcq2xbldn2g45qnb44")
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("\"metadata\""),
                "Should not include metadata when null");
        assertFalse(json.contains("\"revocation_date\""),
                "Should not include revocation_date when null");
    }

    @Test
    void shouldSerializeAllFiveUdfFields() {
        final IssuingCardMetadata metadata = IssuingCardMetadata.builder()
                .udf1("v1").udf2("v2").udf3("v3").udf4("v4").udf5("v5")
                .build();

        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId("crh_d3ozhf43pcq2xbldn2g45qnb44")
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .metadata(metadata)
                .build();

        final String json = serializer.toJson(request);

        for (int i = 1; i <= 5; i++) {
            assertTrue(json.contains("\"udf" + i + "\""), "Should contain udf" + i);
            assertTrue(json.contains("\"v" + i + "\""), "Should contain value for udf" + i);
        }
    }

    // ─── PhysicalCardRequest ─────────────────────────────────────────────────

    @Test
    void shouldSerializePhysicalCardRequestWithMetadata() {
        final PhysicalCardRequest request = PhysicalCardRequest.builder()
                .cardholderId("crh_d3ozhf43pcq2xbldn2g45qnb44")
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .metadata(IssuingCardMetadata.builder()
                        .udf1("physical_card_ref")
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"metadata\""));
        assertTrue(json.contains("\"physical_card_ref\""));
    }

    @Test
    void shouldSerializePhysicalCardRequestWithRevocationDate() {
        final PhysicalCardRequest request = PhysicalCardRequest.builder()
                .cardholderId("crh_d3ozhf43pcq2xbldn2g45qnb44")
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .lifetime(CardLifetime.builder().unit(LifetimeUnit.YEARS).value(3).build())
                .revocationDate(LocalDate.of(2027, 12, 31))
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"revocation_date\""));
        assertTrue(json.contains("2027-12-31"));
    }

    @Test
    void shouldDeserializeSwaggerExampleVirtualCardRequest() {
        final String json = "{"
                + "\"type\":\"virtual\","
                + "\"cardholder_id\":\"crh_d3ozhf43pcq2xbldn2g45qnb44\","
                + "\"card_product_id\":\"pro_7syjig3jq3mezlc3vjrdpfitl4\","
                + "\"lifetime\":{\"unit\":\"Months\",\"value\":6},"
                + "\"reference\":\"X-123456-N11\","
                + "\"metadata\":{\"udf1\":\"metadata1\",\"udf2\":\"metadata2\"},"
                + "\"revocation_date\":\"2027-03-12\""
                + "}";

        final VirtualCardRequest deserialized = serializer.fromJson(json, VirtualCardRequest.class);

        assertNotNull(deserialized);
        assertNotNull(deserialized.getMetadata());
        assertEquals("metadata1", deserialized.getMetadata().getUdf1());
        assertEquals("metadata2", deserialized.getMetadata().getUdf2());
    }
}
