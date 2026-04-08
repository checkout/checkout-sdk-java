package com.checkout.issuing.newfields;

import com.checkout.issuing.cards.requests.create.CardLifetime;
import com.checkout.issuing.cards.requests.create.LifetimeUnit;
import com.checkout.issuing.cards.requests.create.PhysicalCardRequest;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests verifying that new fields ({@code metadata}, {@code revocation_date})
 * are correctly wired into both {@link VirtualCardRequest} and
 * {@link PhysicalCardRequest} builders.
 */
class IssuingCardNewFieldsUnitTest {

    private static final String CARDHOLDER_ID = "crh_d3ozhf43pcq2xbldn2g45qnb44";
    private static final String CARD_PRODUCT_ID = "pro_7syjig3jq3mezlc3vjrdpfitl4";

    // ─── VirtualCardRequest ─────────────────────────────────────────────────

    @Test
    void shouldSetMetadataOnVirtualCardRequestViaBuilder() {
        final IssuingCardMetadata metadata = IssuingCardMetadata.builder()
                .udf1("tier_gold")
                .udf2("region_eu")
                .build();

        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId(CARDHOLDER_ID)
                .cardProductId(CARD_PRODUCT_ID)
                .metadata(metadata)
                .build();

        assertNotNull(request.getMetadata());
        assertEquals("tier_gold", request.getMetadata().getUdf1());
        assertEquals("region_eu", request.getMetadata().getUdf2());
        assertNull(request.getMetadata().getUdf3());
    }

    @Test
    void shouldSetRevocationDateOnVirtualCardRequestViaBuilder() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId(CARDHOLDER_ID)
                .cardProductId(CARD_PRODUCT_ID)
                .revocationDate(LocalDate.of(2027, 6, 15))
                .build();

        assertEquals(LocalDate.of(2027, 6, 15), request.getRevocationDate());
    }

    @Test
    void shouldSetBothNewFieldsOnVirtualCardRequest() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId(CARDHOLDER_ID)
                .cardProductId(CARD_PRODUCT_ID)
                .metadata(IssuingCardMetadata.builder().udf1("v1").udf5("v5").build())
                .revocationDate(LocalDate.of(2028, 1, 1))
                .isSingleUse(true)
                .build();

        assertNotNull(request.getMetadata());
        assertEquals("v1", request.getMetadata().getUdf1());
        assertEquals("v5", request.getMetadata().getUdf5());
        assertEquals(LocalDate.of(2028, 1, 1), request.getRevocationDate());
        assertTrue(request.getIsSingleUse());
    }

    @Test
    void shouldHaveNullMetadataAndRevocationDateByDefault() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId(CARDHOLDER_ID)
                .cardProductId(CARD_PRODUCT_ID)
                .build();

        assertNull(request.getMetadata());
        assertNull(request.getRevocationDate());
    }

    // ─── PhysicalCardRequest ─────────────────────────────────────────────────

    @Test
    void shouldSetMetadataOnPhysicalCardRequestViaBuilder() {
        final IssuingCardMetadata metadata = IssuingCardMetadata.builder()
                .udf1("physical_udf1")
                .udf3("physical_udf3")
                .build();

        final PhysicalCardRequest request = PhysicalCardRequest.builder()
                .cardholderId(CARDHOLDER_ID)
                .cardProductId(CARD_PRODUCT_ID)
                .metadata(metadata)
                .build();

        assertNotNull(request.getMetadata());
        assertEquals("physical_udf1", request.getMetadata().getUdf1());
        assertNull(request.getMetadata().getUdf2());
        assertEquals("physical_udf3", request.getMetadata().getUdf3());
    }

    @Test
    void shouldSetRevocationDateOnPhysicalCardRequestViaBuilder() {
        final PhysicalCardRequest request = PhysicalCardRequest.builder()
                .cardholderId(CARDHOLDER_ID)
                .cardProductId(CARD_PRODUCT_ID)
                .lifetime(CardLifetime.builder().unit(LifetimeUnit.MONTHS).value(18).build())
                .revocationDate(LocalDate.of(2026, 9, 30))
                .build();

        assertEquals(LocalDate.of(2026, 9, 30), request.getRevocationDate());
        assertNotNull(request.getLifetime());
        assertEquals(LifetimeUnit.MONTHS, request.getLifetime().getUnit());
        assertEquals(18, request.getLifetime().getValue());
    }

    @Test
    void shouldSetBothNewFieldsOnPhysicalCardRequest() {
        final PhysicalCardRequest request = PhysicalCardRequest.builder()
                .cardholderId(CARDHOLDER_ID)
                .cardProductId(CARD_PRODUCT_ID)
                .metadata(IssuingCardMetadata.builder().udf2("shipping_priority").build())
                .revocationDate(LocalDate.of(2027, 3, 12))
                .build();

        assertNotNull(request.getMetadata());
        assertEquals("shipping_priority", request.getMetadata().getUdf2());
        assertEquals(LocalDate.of(2027, 3, 12), request.getRevocationDate());
    }

    @Test
    void shouldHaveNullNewFieldsByDefaultOnPhysicalCardRequest() {
        final PhysicalCardRequest request = PhysicalCardRequest.builder()
                .cardholderId(CARDHOLDER_ID)
                .cardProductId(CARD_PRODUCT_ID)
                .build();

        assertNull(request.getMetadata());
        assertNull(request.getRevocationDate());
    }

    // ─── IssuingCardMetadata ─────────────────────────────────────────────────

    @Test
    void shouldBuildIssuingCardMetadataWithAllFields() {
        final IssuingCardMetadata metadata = IssuingCardMetadata.builder()
                .udf1("a").udf2("b").udf3("c").udf4("d").udf5("e")
                .build();

        assertEquals("a", metadata.getUdf1());
        assertEquals("b", metadata.getUdf2());
        assertEquals("c", metadata.getUdf3());
        assertEquals("d", metadata.getUdf4());
        assertEquals("e", metadata.getUdf5());
    }
}
