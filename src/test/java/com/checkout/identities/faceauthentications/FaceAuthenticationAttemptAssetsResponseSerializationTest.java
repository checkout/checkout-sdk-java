package com.checkout.identities.faceauthentications;

import com.checkout.GsonSerializer;
import com.checkout.common.Link;
import com.checkout.identities.entities.AttemptAssetLinks;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptAsset;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptAssetType;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptAssetsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FaceAuthenticationAttemptAssetsResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithAllProperties() {
        final FaceAuthenticationAttemptAssetsResponse response = FaceAuthenticationAttemptAssetsResponse.builder()
                .totalCount(1)
                .skip(0)
                .limit(10)
                .data(Collections.singletonList(FaceAuthenticationAttemptAsset.builder()
                        .type(FaceAuthenticationAttemptAssetType.FACE_IMAGE)
                        .links(AttemptAssetLinks.builder()
                                .assetUrl(new Link("https://example.com/face-image.jpg", null, null))
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
                + "  {\"type\":\"face_image\",\"_links\":{\"asset_url\":{\"href\":\"https://example.com/face-image.jpg\"}}},"
                + "  {\"type\":\"face_video\",\"_links\":{\"asset_url\":{\"href\":\"https://example.com/face-video.mp4\"}}}"
                + "],"
                + "\"_links\":{"
                + "  \"self\":{\"href\":\"https://example.com/assets\"},"
                + "  \"next\":{\"href\":\"https://example.com/assets?skip=10\"}"
                + "}"
                + "}";

        final FaceAuthenticationAttemptAssetsResponse response =
                serializer.fromJson(json, FaceAuthenticationAttemptAssetsResponse.class);

        assertNotNull(response);
        assertEquals(2, response.getTotalCount());
        assertEquals(0, response.getSkip());
        assertEquals(10, response.getLimit());
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals(FaceAuthenticationAttemptAssetType.FACE_IMAGE, response.getData().get(0).getType());
        assertNotNull(response.getData().get(0).getLinks());
        assertNotNull(response.getData().get(0).getLinks().getAssetUrl());
        assertEquals("https://example.com/face-image.jpg", response.getData().get(0).getLinks().getAssetUrl().getHref());
        assertEquals(FaceAuthenticationAttemptAssetType.FACE_VIDEO, response.getData().get(1).getType());
        assertNotNull(response.getSelfLink());
    }

    @Test
    void shouldRoundTripSerialize() {
        final FaceAuthenticationAttemptAssetsResponse original = FaceAuthenticationAttemptAssetsResponse.builder()
                .totalCount(1)
                .skip(5)
                .limit(20)
                .data(Collections.singletonList(FaceAuthenticationAttemptAsset.builder()
                        .type(FaceAuthenticationAttemptAssetType.FACE_VIDEO)
                        .links(AttemptAssetLinks.builder()
                                .assetUrl(new Link("https://example.com/face-video.mp4", null, null))
                                .build())
                        .build()))
                .build();

        final String json = serializer.toJson(original);
        final FaceAuthenticationAttemptAssetsResponse deserialized =
                serializer.fromJson(json, FaceAuthenticationAttemptAssetsResponse.class);

        assertTrue(json.contains("\"total_count\":1"));
        assertTrue(json.contains("\"asset_url\""));
        assertEquals(1, deserialized.getTotalCount());
        assertEquals(5, deserialized.getSkip());
        assertEquals(20, deserialized.getLimit());
        assertEquals(1, deserialized.getData().size());
        assertEquals(FaceAuthenticationAttemptAssetType.FACE_VIDEO, deserialized.getData().get(0).getType());
        assertEquals("https://example.com/face-video.mp4", deserialized.getData().get(0).getLinks().getAssetUrl().getHref());
    }

    @ParameterizedTest
    @EnumSource(FaceAuthenticationAttemptAssetType.class)
    void shouldSerializeEachAssetTypeToSwaggerValue(final FaceAuthenticationAttemptAssetType type) {
        final FaceAuthenticationAttemptAsset asset = FaceAuthenticationAttemptAsset.builder()
                .type(type)
                .build();

        final String json = serializer.toJson(asset);

        assertTrue(json.contains("\"type\":\"" + type.name().toLowerCase() + "\""));
    }
}
