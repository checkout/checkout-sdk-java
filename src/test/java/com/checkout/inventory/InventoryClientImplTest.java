package com.checkout.inventory;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.inventory.request.InventoryAdjustmentRequest;
import com.checkout.inventory.request.InventoryReservationRequest;
import com.checkout.inventory.request.InventorySetLevelsRequest;
import com.checkout.inventory.request.InventorySetProductRequest;
import com.checkout.inventory.response.InventoryLevels;
import com.checkout.inventory.response.InventoryProductKnowledge;
import com.checkout.inventory.response.InventoryReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Verifies InventoryClientImpl uses SdkAuthorizationType.OAUTH exclusively (the
 * {@code agentic:inventory} scope) with no ApiSecretKey/ApiPublicKey fallback, and that each of
 * the 10 inventory operations builds the expected path and forwards to ApiClient correctly.
 */
@ExtendWith(MockitoExtension.class)
class InventoryClientImplTest {

    private InventoryClient client;

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
        client = new InventoryClientImpl(apiClient, configuration);
    }

    @Test
    void shouldAdjustInventory() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventoryAdjustmentRequest request = InventoryAdjustmentRequest.builder()
                .variantId("var_123")
                .delta(-3)
                .reason("damaged in warehouse")
                .build();
        final InventoryLevels response = mock(InventoryLevels.class);

        when(apiClient.postAsync(eq("inventory/adjustments"), eq(authorization), eq(InventoryLevels.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryLevels> future = client.adjustInventory(request);

        assertEquals(response, future.get());
    }

    @Test
    void shouldAdjustInventoryWithIdempotencyKey() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventoryAdjustmentRequest request = InventoryAdjustmentRequest.builder()
                .variantId("var_123")
                .delta(-3)
                .reason("damaged in warehouse")
                .build();
        final InventoryLevels response = mock(InventoryLevels.class);

        when(apiClient.postAsync(eq("inventory/adjustments"), eq(authorization), eq(InventoryLevels.class),
                eq(request), eq("idem-key")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryLevels> future = client.adjustInventory(request, "idem-key");

        assertEquals(response, future.get());
    }

    @Test
    void shouldGetInventoryLevels() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventoryLevels response = mock(InventoryLevels.class);

        when(apiClient.getAsync(eq("inventory/var_123"), eq(authorization), eq(InventoryLevels.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryLevels> future = client.getInventoryLevels("var_123");

        assertEquals(response, future.get());
    }

    @Test
    void shouldSetInventoryLevels() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventorySetLevelsRequest request = InventorySetLevelsRequest.builder().onHand(25).build();
        final InventoryLevels response = mock(InventoryLevels.class);

        when(apiClient.putAsync(eq("inventory/var_123"), eq(authorization), eq(InventoryLevels.class), eq(request)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryLevels> future = client.setInventoryLevels("var_123", request);

        assertEquals(response, future.get());
    }

    @Test
    void shouldCreateInventoryReservation() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventoryReservationRequest request = InventoryReservationRequest.builder()
                .ownerType("ucp_session")
                .ownerReference("cs_8f42")
                .items(java.util.List.of(InventoryReservationItem.builder().variantId("var_123").quantity(2).build()))
                .build();
        final InventoryReservation response = mock(InventoryReservation.class);

        when(apiClient.postAsync(eq("inventory/reservations"), eq(authorization), eq(InventoryReservation.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryReservation> future = client.createInventoryReservation(request);

        assertEquals(response, future.get());
    }

    @Test
    void shouldGetInventoryReservation() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventoryReservation response = mock(InventoryReservation.class);

        when(apiClient.getAsync(eq("inventory/reservations/rsv_123"), eq(authorization), eq(InventoryReservation.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryReservation> future = client.getInventoryReservation("rsv_123");

        assertEquals(response, future.get());
    }

    @Test
    void shouldCommitInventoryReservation() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventoryReservation response = mock(InventoryReservation.class);

        when(apiClient.postAsync(eq("inventory/reservations/rsv_123/commit"), eq(authorization),
                eq(InventoryReservation.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryReservation> future = client.commitInventoryReservation("rsv_123");

        assertEquals(response, future.get());
    }

    @Test
    void shouldReleaseInventoryReservation() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventoryReservation response = mock(InventoryReservation.class);

        when(apiClient.postAsync(eq("inventory/reservations/rsv_123/release"), eq(authorization),
                eq(InventoryReservation.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryReservation> future = client.releaseInventoryReservation("rsv_123");

        assertEquals(response, future.get());
    }

    @Test
    void shouldGetInventoryProduct() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventoryProductKnowledge response = mock(InventoryProductKnowledge.class);

        when(apiClient.getAsync(eq("inventory/var_123/product"), eq(authorization), eq(InventoryProductKnowledge.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryProductKnowledge> future = client.getInventoryProduct("var_123");

        assertEquals(response, future.get());
    }

    @Test
    void shouldSetInventoryProduct() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final InventorySetProductRequest request = InventorySetProductRequest.builder()
                .title("Belt").description("desc").productUrl("https://example.com").imageUrl("https://example.com/i.jpg")
                .build();
        final InventoryProductKnowledge response = mock(InventoryProductKnowledge.class);

        when(apiClient.putAsync(eq("inventory/var_123/product"), eq(authorization), eq(InventoryProductKnowledge.class), eq(request)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InventoryProductKnowledge> future = client.setInventoryProduct("var_123", request);

        assertEquals(response, future.get());
    }

    @Test
    void shouldDeleteInventoryProduct() throws ExecutionException, InterruptedException {
        setupMockCredentials();

        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.deleteAsync(eq("inventory/var_123/product"), eq(authorization)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.deleteInventoryProduct("var_123");

        assertEquals(response, future.get());
    }

    // Common methods

    private void setupMockCredentials() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
    }

}
