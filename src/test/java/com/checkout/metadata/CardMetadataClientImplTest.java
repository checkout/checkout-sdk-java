package com.checkout.metadata;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.metadata.card.CardMetadataFormatType;
import com.checkout.metadata.card.CardMetadataRequest;
import com.checkout.metadata.card.CardMetadataResponse;
import com.checkout.metadata.card.source.CardMetadataBinSource;
import com.checkout.metadata.card.source.CardMetadataCardSource;
import com.checkout.metadata.card.source.CardMetadataIdSource;
import com.checkout.metadata.card.source.CardMetadataTokenSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardMetadataClientImplTest {

    private static final String METADATA_CARD_PATH = "metadata/card";

    private MetadataClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new MetadataClientImpl(apiClient, configuration);
    }

    // ─── Async ──────────────────────────────────────────────────────────────

    @Test
    void shouldRequestCardMetadataWithBinSourceAsync() throws ExecutionException, InterruptedException {
        final CardMetadataRequest request = buildBinRequest();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.postAsync(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CardMetadataResponse result = client.requestCardMetadata(request).get();

        assertNotNull(result);
        assertEquals(response, result);
        verify(apiClient).postAsync(eq(METADATA_CARD_PATH), eq(authorization),
                eq(CardMetadataResponse.class), eq(request), isNull());
    }

    @Test
    void shouldRequestCardMetadataWithCardSourceAsync() throws ExecutionException, InterruptedException {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder().number("4543474002249996").build())
                .format(CardMetadataFormatType.BASIC)
                .build();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.postAsync(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CardMetadataResponse result = client.requestCardMetadata(request).get();

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldRequestCardMetadataWithTokenSourceAsync() throws ExecutionException, InterruptedException {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataTokenSource.builder().token("tok_ubfj2q76miwundwlk72vxt2i7q").build())
                .format(CardMetadataFormatType.BASIC)
                .build();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.postAsync(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CardMetadataResponse result = client.requestCardMetadata(request).get();

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldRequestCardMetadataWithIdSourceAsync() throws ExecutionException, InterruptedException {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataIdSource.builder().id("src_wmlfc3zyhqzehihu7giusaaawu").build())
                .format(CardMetadataFormatType.CARD_PAYOUTS)
                .build();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.postAsync(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CardMetadataResponse result = client.requestCardMetadata(request).get();

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldIncludeReferenceFieldInRequestAsync() throws ExecutionException, InterruptedException {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder().bin("454347").build())
                .format(CardMetadataFormatType.BASIC)
                .reference("ORD-5023-4E89")
                .build();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.postAsync(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CardMetadataResponse result = client.requestCardMetadata(request).get();

        assertNotNull(result);
        verify(apiClient).postAsync(eq(METADATA_CARD_PATH), eq(authorization),
                eq(CardMetadataResponse.class), eq(request), isNull());
    }

    @Test
    @org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
    void shouldThrowWhenRequestIsNullAsync() {
        assertThrows(Exception.class, () -> client.requestCardMetadata(null));
    }

    // ─── Sync ───────────────────────────────────────────────────────────────

    @Test
    void shouldRequestCardMetadataWithBinSourceSync() {
        final CardMetadataRequest request = buildBinRequest();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.post(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final CardMetadataResponse result = client.requestCardMetadataSync(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(apiClient).post(eq(METADATA_CARD_PATH), eq(authorization),
                eq(CardMetadataResponse.class), eq(request), isNull());
    }

    @Test
    void shouldRequestCardMetadataWithCardSourceSync() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder().number("4543474002249996").build())
                .format(CardMetadataFormatType.CARD_PAYOUTS)
                .build();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.post(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final CardMetadataResponse result = client.requestCardMetadataSync(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldRequestCardMetadataWithTokenSourceSync() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataTokenSource.builder().token("tok_ubfj2q76miwundwlk72vxt2i7q").build())
                .format(CardMetadataFormatType.BASIC)
                .build();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.post(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final CardMetadataResponse result = client.requestCardMetadataSync(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldRequestCardMetadataWithIdSourceSync() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataIdSource.builder().id("src_wmlfc3zyhqzehihu7giusaaawu").build())
                .format(CardMetadataFormatType.CARD_PAYOUTS)
                .build();
        final CardMetadataResponse response = new CardMetadataResponse();

        when(apiClient.post(eq(METADATA_CARD_PATH), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final CardMetadataResponse result = client.requestCardMetadataSync(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    @org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
    void shouldThrowWhenRequestIsNullSync() {
        assertThrows(Exception.class, () -> client.requestCardMetadataSync(null));
    }

    // ─── Helpers ────────────────────────────────────────────────────────────

    private CardMetadataRequest buildBinRequest() {
        return CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder().bin("454347").build())
                .format(CardMetadataFormatType.BASIC)
                .build();
    }
}
