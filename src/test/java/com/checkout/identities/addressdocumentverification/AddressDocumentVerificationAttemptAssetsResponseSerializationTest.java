package com.checkout.identities.addressdocumentverification;

import com.checkout.GsonSerializer;
import com.checkout.common.Link;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptAsset;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptAssetType;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptAssetsResponse;
import com.checkout.identities.entities.AttemptAssetLinks;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GSON serialization tests for AddressDocumentVerificationAttemptAssetsResponse.
 *
 * Swagger reference:
 * GET /address-document-verifications/{address_document_verification_id}/attempts/{attempt_id}/assets
 * Schemas: AdvAttemptAssets, AdvAttemptAsset.
 */
class AddressDocumentVerificationAttemptAssetsResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithAllProperties() {
        final AddressDocumentVerificationAttemptAssetsResponse response =
                AddressDocumentVerificationAttemptAssetsResponse.builder()
                        .totalCount(1)
                        .skip(0)
                        .limit(10)
                        .data(Collections.singletonList(AddressDocumentVerificationAttemptAsset.builder()
                                .type(AddressDocumentVerificationAttemptAssetType.DOCUMENT)
                                .links(AttemptAssetLinks.builder()
                                        .assetUrl(new Link("https://example.com/address-document.png", null, null))
                                        .build())
                                .build()))
                        .build();

        assertDoesNotThrow(() -> serializer.toJson(response));
    }

    /**
     * Deserializes the spec's adv_attempt_assets_response_body example verbatim. The next and
     * previous hrefs are truncated with "?..." in the specification itself.
     */
    @Test
    void shouldDeserializeFromSwaggerExample() {
        final String json = "{"
                + "\"total_count\":1,"
                + "\"skip\":0,"
                + "\"limit\":10,"
                + "\"data\":["
                + "  {\"type\":\"document\",\"_links\":{\"asset_url\":{\"href\":\"https://storage-b.env.ubble.ai/ubble-ai/NDYOOVHGZPAQ/a54b3393-f02a-47c9-a9c5-2f6ee73560e1/bb603e2f-5de9-40f2-9631-8285a33c24c0/address_document.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600\"}}}"
                + "],"
                + "\"_links\":{"
                + "  \"self\":{\"href\":\"https://identity-verification.checkout.com/address-document-verifications/adv_tkoi5db4hryu5cei5vwoabr7we/attempts/adva_tkoi5db4hryu5cei5vwoabr7we/assets\"},"
                + "  \"next\":{\"href\":\"https://identity-verification.checkout.com/address-document-verifications/adv_tkoi5db4hryu5cei5vwoabr7we/attempts/adva_tkoi5db4hryu5cei5vwoabr7we/assets?...\"},"
                + "  \"previous\":{\"href\":\"https://identity-verification.checkout.com/address-document-verifications/adv_tkoi5db4hryu5cei5vwoabr7we/attempts/adva_tkoi5db4hryu5cei5vwoabr7we/assets?...\"}"
                + "}"
                + "}";

        final AddressDocumentVerificationAttemptAssetsResponse response =
                serializer.fromJson(json, AddressDocumentVerificationAttemptAssetsResponse.class);

        assertNotNull(response);
        assertEquals(1, response.getTotalCount());
        assertEquals(0, response.getSkip());
        assertEquals(10, response.getLimit());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals(AddressDocumentVerificationAttemptAssetType.DOCUMENT, response.getData().get(0).getType());
        assertNotNull(response.getData().get(0).getLinks());
        assertNotNull(response.getData().get(0).getLinks().getAssetUrl());
        assertTrue(response.getData().get(0).getLinks().getAssetUrl().getHref().contains("address_document.png"));
    }

    @Test
    void shouldRoundTripSerialize() {
        final AddressDocumentVerificationAttemptAssetsResponse original =
                AddressDocumentVerificationAttemptAssetsResponse.builder()
                        .totalCount(3)
                        .skip(6)
                        .limit(5)
                        .data(Collections.singletonList(AddressDocumentVerificationAttemptAsset.builder()
                                .type(AddressDocumentVerificationAttemptAssetType.DOCUMENT)
                                .links(AttemptAssetLinks.builder()
                                        .assetUrl(new Link("https://example.com/address-document.png", null, null))
                                        .build())
                                .build()))
                        .build();

        final String json = serializer.toJson(original);
        final AddressDocumentVerificationAttemptAssetsResponse deserialized =
                serializer.fromJson(json, AddressDocumentVerificationAttemptAssetsResponse.class);

        assertTrue(json.contains("\"total_count\":3"));
        assertTrue(json.contains("\"asset_url\""));
        assertEquals(3, deserialized.getTotalCount());
        assertEquals(6, deserialized.getSkip());
        assertEquals(5, deserialized.getLimit());
        assertEquals(1, deserialized.getData().size());
        assertEquals(AddressDocumentVerificationAttemptAssetType.DOCUMENT, deserialized.getData().get(0).getType());
        assertEquals("https://example.com/address-document.png",
                deserialized.getData().get(0).getLinks().getAssetUrl().getHref());
    }

    /**
     * data has minItems 0, so an empty page is valid and must not be asserted non empty.
     */
    @Test
    void shouldDeserializeEmptyDataPage() {
        final String json = "{\"total_count\":0,\"skip\":0,\"limit\":10,\"data\":[]}";

        final AddressDocumentVerificationAttemptAssetsResponse response =
                serializer.fromJson(json, AddressDocumentVerificationAttemptAssetsResponse.class);

        assertNotNull(response);
        assertEquals(0, response.getTotalCount());
        assertNotNull(response.getData());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void shouldSerializeTheOnlyAssetTypeToItsSwaggerValue() {
        final AddressDocumentVerificationAttemptAsset asset = AddressDocumentVerificationAttemptAsset.builder()
                .type(AddressDocumentVerificationAttemptAssetType.DOCUMENT)
                .build();

        final String json = serializer.toJson(asset);

        assertTrue(json.contains("\"type\":\"document\""));
    }
}
