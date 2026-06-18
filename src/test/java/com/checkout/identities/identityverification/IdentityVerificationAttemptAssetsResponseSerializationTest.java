package com.checkout.identities.identityverification;

import com.checkout.GsonSerializer;
import com.checkout.common.Link;
import com.checkout.identities.entities.AttemptAssetLinks;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptAsset;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptAssetType;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptAssetsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdentityVerificationAttemptAssetsResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithAllProperties() {
        final IdentityVerificationAttemptAssetsResponse response = IdentityVerificationAttemptAssetsResponse.builder()
                .totalCount(1)
                .skip(0)
                .limit(10)
                .data(Collections.singletonList(IdentityVerificationAttemptAsset.builder()
                        .type(IdentityVerificationAttemptAssetType.DOCUMENT_FRONT_IMAGE)
                        .links(AttemptAssetLinks.builder()
                                .assetUrl(new Link("https://example.com/document-front.jpg", null, null))
                                .build())
                        .build()))
                .build();

        assertDoesNotThrow(() -> serializer.toJson(response));
    }

    @Test
    void shouldDeserializeFromSwaggerExample() {
        final String json = "{"
                + "\"total_count\":2,"
                + "\"skip\":0,"
                + "\"limit\":10,"
                + "\"data\":["
                + "  {\"type\":\"document_front_image\",\"_links\":{\"asset_url\":{\"href\":\"https://example.com/document-front.jpg\"}}},"
                + "  {\"type\":\"face_image\",\"_links\":{\"asset_url\":{\"href\":\"https://example.com/face-image.jpg\"}}}"
                + "],"
                + "\"_links\":{"
                + "  \"self\":{\"href\":\"https://example.com/assets\"},"
                + "  \"previous\":{\"href\":\"https://example.com/assets?skip=0\"}"
                + "}"
                + "}";

        final IdentityVerificationAttemptAssetsResponse response =
                serializer.fromJson(json, IdentityVerificationAttemptAssetsResponse.class);

        assertNotNull(response);
        assertEquals(2, response.getTotalCount());
        assertEquals(0, response.getSkip());
        assertEquals(10, response.getLimit());
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals(IdentityVerificationAttemptAssetType.DOCUMENT_FRONT_IMAGE, response.getData().get(0).getType());
        assertNotNull(response.getData().get(0).getLinks());
        assertNotNull(response.getData().get(0).getLinks().getAssetUrl());
        assertEquals("https://example.com/document-front.jpg", response.getData().get(0).getLinks().getAssetUrl().getHref());
        assertEquals(IdentityVerificationAttemptAssetType.FACE_IMAGE, response.getData().get(1).getType());
        assertNotNull(response.getSelfLink());
    }

    @Test
    void shouldRoundTripSerialize() {
        final IdentityVerificationAttemptAssetsResponse original = IdentityVerificationAttemptAssetsResponse.builder()
                .totalCount(1)
                .skip(5)
                .limit(20)
                .data(Collections.singletonList(IdentityVerificationAttemptAsset.builder()
                        .type(IdentityVerificationAttemptAssetType.SECONDARY_DOCUMENT_SIGNATURE_IMAGE)
                        .links(AttemptAssetLinks.builder()
                                .assetUrl(new Link("https://example.com/signature.jpg", null, null))
                                .build())
                        .build()))
                .build();

        final String json = serializer.toJson(original);
        final IdentityVerificationAttemptAssetsResponse deserialized =
                serializer.fromJson(json, IdentityVerificationAttemptAssetsResponse.class);

        assertTrue(json.contains("\"total_count\":1"));
        assertTrue(json.contains("\"asset_url\""));
        assertEquals(1, deserialized.getTotalCount());
        assertEquals(5, deserialized.getSkip());
        assertEquals(20, deserialized.getLimit());
        assertEquals(1, deserialized.getData().size());
        assertEquals(IdentityVerificationAttemptAssetType.SECONDARY_DOCUMENT_SIGNATURE_IMAGE, deserialized.getData().get(0).getType());
        assertEquals("https://example.com/signature.jpg", deserialized.getData().get(0).getLinks().getAssetUrl().getHref());
    }

    @ParameterizedTest
    @EnumSource(IdentityVerificationAttemptAssetType.class)
    void shouldSerializeEachAssetTypeToSwaggerValue(final IdentityVerificationAttemptAssetType type) {
        final IdentityVerificationAttemptAsset asset = IdentityVerificationAttemptAsset.builder()
                .type(type)
                .build();

        final String json = serializer.toJson(asset);

        assertTrue(json.contains("\"type\":\"" + type.name().toLowerCase() + "\""));
    }
}
