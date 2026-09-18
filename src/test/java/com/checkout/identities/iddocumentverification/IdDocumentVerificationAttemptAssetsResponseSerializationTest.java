package com.checkout.identities.iddocumentverification;

import com.checkout.GsonSerializer;
import com.checkout.common.Link;
import com.checkout.identities.entities.AttemptAssetLinks;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptAsset;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptAssetType;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptAssetsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GSON serialization tests for IdDocumentVerificationAttemptAssetsResponse.
 *
 * Swagger reference:
 * GET /id-document-verifications/{id_document_verification_id}/attempts/{attempt_id}/assets
 * Schemas: IddvAttemptAssets, IddvAttemptAsset.
 */
class IdDocumentVerificationAttemptAssetsResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithAllProperties() {
        final IdDocumentVerificationAttemptAssetsResponse response =
                IdDocumentVerificationAttemptAssetsResponse.builder()
                        .totalCount(2)
                        .skip(0)
                        .limit(10)
                        .data(Arrays.asList(
                                IdDocumentVerificationAttemptAsset.builder()
                                        .type(IdDocumentVerificationAttemptAssetType.DOCUMENT_FRONT_IMAGE)
                                        .links(AttemptAssetLinks.builder()
                                                .assetUrl(new Link("https://example.com/document_front.png", null, null))
                                                .build())
                                        .build(),
                                IdDocumentVerificationAttemptAsset.builder()
                                        .type(IdDocumentVerificationAttemptAssetType.DOCUMENT_BACK_IMAGE)
                                        .links(AttemptAssetLinks.builder()
                                                .assetUrl(new Link("https://example.com/document_back.png", null, null))
                                                .build())
                                        .build()))
                        .build();

        assertDoesNotThrow(() -> serializer.toJson(response));
    }

    /**
     * Deserializes the spec's iddv_attempt_assets_response_body example verbatim. The next and
     * previous hrefs are truncated with "?..." in the specification itself.
     */
    @Test
    void shouldDeserializeFromSwaggerExample() {
        final String json = "{"
                + "\"total_count\":2,"
                + "\"skip\":0,"
                + "\"limit\":10,"
                + "\"data\":["
                + "  {\"type\":\"document_front_image\",\"_links\":{\"asset_url\":{\"href\":\"https://storage-b.env.ubble.ai/ubble-ai/NDYOOVHGZPAQ/a54b3393-f02a-47c9-a9c5-2f6ee73560e1/bb603e2f-5de9-40f2-9631-8285a33c24c0/document_front.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600\"}}},"
                + "  {\"type\":\"document_back_image\",\"_links\":{\"asset_url\":{\"href\":\"https://storage-b.env.ubble.ai/ubble-ai/NDYOOVHGZPAQ/a54b3393-f02a-47c9-a9c5-2f6ee73560e1/bb603e2f-5de9-40f2-9631-8285a33c24c0/document_back.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600\"}}}"
                + "],"
                + "\"_links\":{"
                + "  \"self\":{\"href\":\"https://identity-verification.checkout.com/id-document-verifications/iddv_tkoi5db4hryu5cei5vwoabr7we/attempts/datp_tkoi5db4hryu5cei5vwoabraio/assets\"},"
                + "  \"next\":{\"href\":\"https://identity-verification.checkout.com/id-document-verifications/iddv_tkoi5db4hryu5cei5vwoabr7we/attempts/datp_tkoi5db4hryu5cei5vwoabraio/assets?...\"},"
                + "  \"previous\":{\"href\":\"https://identity-verification.checkout.com/id-document-verifications/iddv_tkoi5db4hryu5cei5vwoabr7we/attempts/datp_tkoi5db4hryu5cei5vwoabraio/assets?...\"}"
                + "}"
                + "}";

        final IdDocumentVerificationAttemptAssetsResponse response =
                serializer.fromJson(json, IdDocumentVerificationAttemptAssetsResponse.class);

        assertNotNull(response);
        assertEquals(2, response.getTotalCount());
        assertEquals(0, response.getSkip());
        assertEquals(10, response.getLimit());
        assertEquals(2, response.getData().size());
        assertEquals(IdDocumentVerificationAttemptAssetType.DOCUMENT_FRONT_IMAGE, response.getData().get(0).getType());
        assertTrue(response.getData().get(0).getLinks().getAssetUrl().getHref().contains("document_front.png"));
        assertEquals(IdDocumentVerificationAttemptAssetType.DOCUMENT_BACK_IMAGE, response.getData().get(1).getType());
        assertTrue(response.getData().get(1).getLinks().getAssetUrl().getHref().contains("document_back.png"));
    }

    @Test
    void shouldRoundTripSerialize() {
        final IdDocumentVerificationAttemptAssetsResponse original =
                IdDocumentVerificationAttemptAssetsResponse.builder()
                        .totalCount(2)
                        .skip(1)
                        .limit(4)
                        .data(Collections.singletonList(IdDocumentVerificationAttemptAsset.builder()
                                .type(IdDocumentVerificationAttemptAssetType.DOCUMENT_BACK_IMAGE)
                                .links(AttemptAssetLinks.builder()
                                        .assetUrl(new Link("https://example.com/document_back.png", null, null))
                                        .build())
                                .build()))
                        .build();

        final String json = serializer.toJson(original);
        final IdDocumentVerificationAttemptAssetsResponse deserialized =
                serializer.fromJson(json, IdDocumentVerificationAttemptAssetsResponse.class);

        assertTrue(json.contains("\"total_count\":2"));
        assertTrue(json.contains("\"asset_url\""));
        assertEquals(2, deserialized.getTotalCount());
        assertEquals(1, deserialized.getSkip());
        assertEquals(4, deserialized.getLimit());
        assertEquals(IdDocumentVerificationAttemptAssetType.DOCUMENT_BACK_IMAGE,
                deserialized.getData().get(0).getType());
    }

    /**
     * data has minItems 0, so an empty page is valid and must not be asserted non empty.
     */
    @Test
    void shouldDeserializeEmptyDataPage() {
        final String json = "{\"total_count\":0,\"skip\":0,\"limit\":10,\"data\":[]}";

        final IdDocumentVerificationAttemptAssetsResponse response =
                serializer.fromJson(json, IdDocumentVerificationAttemptAssetsResponse.class);

        assertNotNull(response);
        assertEquals(0, response.getTotalCount());
        assertTrue(response.getData().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "DOCUMENT_FRONT_IMAGE,document_front_image",
            "DOCUMENT_BACK_IMAGE,document_back_image"
    })
    void shouldSerializeEachAssetTypeToSwaggerValue(final IdDocumentVerificationAttemptAssetType type,
                                                    final String expected) {
        final IdDocumentVerificationAttemptAsset asset =
                IdDocumentVerificationAttemptAsset.builder().type(type).build();

        final String json = serializer.toJson(asset);

        assertTrue(json.contains("\"type\":\"" + expected + "\""));
    }
}
