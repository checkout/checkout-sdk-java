package com.checkout.inventory;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.inventory.request.InventoryAdjustmentRequest;
import com.checkout.inventory.request.InventoryLevelsQueryFilter;
import com.checkout.inventory.request.InventoryReservationRequest;
import com.checkout.inventory.request.InventorySetLevelsRequest;
import com.checkout.inventory.request.InventorySetProductRequest;
import com.checkout.inventory.response.InventoryLevels;
import com.checkout.inventory.response.InventoryProductKnowledge;
import com.checkout.inventory.response.InventoryReservation;

import java.util.concurrent.CompletableFuture;

/**
 * Every operation on this client requires the {@code agentic:inventory} OAuth scope; see
 * {@link SdkAuthorizationType#OAUTH}. There is no secret-key or public-key fallback.
 */
public class InventoryClientImpl extends AbstractClient implements InventoryClient {

    private static final String INVENTORY_PATH = "inventory";
    private static final String ADJUSTMENTS_PATH = "adjustments";
    private static final String RESERVATIONS_PATH = "reservations";
    private static final String COMMIT_PATH = "commit";
    private static final String RELEASE_PATH = "release";
    private static final String PRODUCT_PATH = "product";

    public InventoryClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
    }

    @Override
    public CompletableFuture<InventoryLevels> adjustInventory(final InventoryAdjustmentRequest request) {
        return adjustInventory(request, null);
    }

    @Override
    public CompletableFuture<InventoryLevels> adjustInventory(final InventoryAdjustmentRequest request, final String idempotencyKey) {
        CheckoutUtils.validateParams("request", request);
        return apiClient.postAsync(
                buildPath(INVENTORY_PATH, ADJUSTMENTS_PATH),
                sdkAuthorization(),
                InventoryLevels.class,
                request,
                idempotencyKey);
    }

    @Override
    public CompletableFuture<InventoryLevels> getInventoryLevels(final String variantId) {
        CheckoutUtils.validateParams("variantId", variantId);
        return apiClient.getAsync(buildPath(INVENTORY_PATH, variantId), sdkAuthorization(), InventoryLevels.class);
    }

    @Override
    public CompletableFuture<InventoryLevels> getInventoryLevels(final String variantId, final InventoryLevelsQueryFilter filter) {
        CheckoutUtils.validateParams("variantId", variantId, "filter", filter);
        return apiClient.queryAsync(buildPath(INVENTORY_PATH, variantId), sdkAuthorization(), filter, InventoryLevels.class);
    }

    @Override
    public CompletableFuture<InventoryLevels> setInventoryLevels(final String variantId, final InventorySetLevelsRequest request) {
        CheckoutUtils.validateParams("variantId", variantId, "request", request);
        return apiClient.putAsync(buildPath(INVENTORY_PATH, variantId), sdkAuthorization(), InventoryLevels.class, request);
    }

    @Override
    public CompletableFuture<InventoryReservation> createInventoryReservation(final InventoryReservationRequest request) {
        return createInventoryReservation(request, null);
    }

    @Override
    public CompletableFuture<InventoryReservation> createInventoryReservation(final InventoryReservationRequest request, final String idempotencyKey) {
        CheckoutUtils.validateParams("request", request);
        return apiClient.postAsync(
                buildPath(INVENTORY_PATH, RESERVATIONS_PATH),
                sdkAuthorization(),
                InventoryReservation.class,
                request,
                idempotencyKey);
    }

    @Override
    public CompletableFuture<InventoryReservation> getInventoryReservation(final String reservationId) {
        CheckoutUtils.validateParams("reservationId", reservationId);
        return apiClient.getAsync(buildPath(INVENTORY_PATH, RESERVATIONS_PATH, reservationId), sdkAuthorization(), InventoryReservation.class);
    }

    @Override
    public CompletableFuture<InventoryReservation> commitInventoryReservation(final String reservationId) {
        CheckoutUtils.validateParams("reservationId", reservationId);
        return apiClient.postAsync(
                buildPath(INVENTORY_PATH, RESERVATIONS_PATH, reservationId, COMMIT_PATH),
                sdkAuthorization(),
                InventoryReservation.class,
                null,
                null);
    }

    @Override
    public CompletableFuture<InventoryReservation> releaseInventoryReservation(final String reservationId) {
        CheckoutUtils.validateParams("reservationId", reservationId);
        return apiClient.postAsync(
                buildPath(INVENTORY_PATH, RESERVATIONS_PATH, reservationId, RELEASE_PATH),
                sdkAuthorization(),
                InventoryReservation.class,
                null,
                null);
    }

    @Override
    public CompletableFuture<InventoryProductKnowledge> getInventoryProduct(final String variantId) {
        CheckoutUtils.validateParams("variantId", variantId);
        return apiClient.getAsync(buildPath(INVENTORY_PATH, variantId, PRODUCT_PATH), sdkAuthorization(), InventoryProductKnowledge.class);
    }

    @Override
    public CompletableFuture<InventoryProductKnowledge> setInventoryProduct(final String variantId, final InventorySetProductRequest request) {
        CheckoutUtils.validateParams("variantId", variantId, "request", request);
        return apiClient.putAsync(buildPath(INVENTORY_PATH, variantId, PRODUCT_PATH), sdkAuthorization(), InventoryProductKnowledge.class, request);
    }

    @Override
    public CompletableFuture<EmptyResponse> deleteInventoryProduct(final String variantId) {
        CheckoutUtils.validateParams("variantId", variantId);
        return apiClient.deleteAsync(buildPath(INVENTORY_PATH, variantId, PRODUCT_PATH), sdkAuthorization());
    }

    // Synchronous methods

    @Override
    public InventoryLevels adjustInventorySync(final InventoryAdjustmentRequest request) {
        return adjustInventorySync(request, null);
    }

    @Override
    public InventoryLevels adjustInventorySync(final InventoryAdjustmentRequest request, final String idempotencyKey) {
        CheckoutUtils.validateParams("request", request);
        return apiClient.post(
                buildPath(INVENTORY_PATH, ADJUSTMENTS_PATH),
                sdkAuthorization(),
                InventoryLevels.class,
                request,
                idempotencyKey);
    }

    @Override
    public InventoryLevels getInventoryLevelsSync(final String variantId) {
        CheckoutUtils.validateParams("variantId", variantId);
        return apiClient.get(buildPath(INVENTORY_PATH, variantId), sdkAuthorization(), InventoryLevels.class);
    }

    @Override
    public InventoryLevels getInventoryLevelsSync(final String variantId, final InventoryLevelsQueryFilter filter) {
        CheckoutUtils.validateParams("variantId", variantId, "filter", filter);
        return apiClient.query(buildPath(INVENTORY_PATH, variantId), sdkAuthorization(), filter, InventoryLevels.class);
    }

    @Override
    public InventoryLevels setInventoryLevelsSync(final String variantId, final InventorySetLevelsRequest request) {
        CheckoutUtils.validateParams("variantId", variantId, "request", request);
        return apiClient.put(buildPath(INVENTORY_PATH, variantId), sdkAuthorization(), InventoryLevels.class, request);
    }

    @Override
    public InventoryReservation createInventoryReservationSync(final InventoryReservationRequest request) {
        return createInventoryReservationSync(request, null);
    }

    @Override
    public InventoryReservation createInventoryReservationSync(final InventoryReservationRequest request, final String idempotencyKey) {
        CheckoutUtils.validateParams("request", request);
        return apiClient.post(
                buildPath(INVENTORY_PATH, RESERVATIONS_PATH),
                sdkAuthorization(),
                InventoryReservation.class,
                request,
                idempotencyKey);
    }

    @Override
    public InventoryReservation getInventoryReservationSync(final String reservationId) {
        CheckoutUtils.validateParams("reservationId", reservationId);
        return apiClient.get(buildPath(INVENTORY_PATH, RESERVATIONS_PATH, reservationId), sdkAuthorization(), InventoryReservation.class);
    }

    @Override
    public InventoryReservation commitInventoryReservationSync(final String reservationId) {
        CheckoutUtils.validateParams("reservationId", reservationId);
        return apiClient.post(
                buildPath(INVENTORY_PATH, RESERVATIONS_PATH, reservationId, COMMIT_PATH),
                sdkAuthorization(),
                InventoryReservation.class,
                null,
                null);
    }

    @Override
    public InventoryReservation releaseInventoryReservationSync(final String reservationId) {
        CheckoutUtils.validateParams("reservationId", reservationId);
        return apiClient.post(
                buildPath(INVENTORY_PATH, RESERVATIONS_PATH, reservationId, RELEASE_PATH),
                sdkAuthorization(),
                InventoryReservation.class,
                null,
                null);
    }

    @Override
    public InventoryProductKnowledge getInventoryProductSync(final String variantId) {
        CheckoutUtils.validateParams("variantId", variantId);
        return apiClient.get(buildPath(INVENTORY_PATH, variantId, PRODUCT_PATH), sdkAuthorization(), InventoryProductKnowledge.class);
    }

    @Override
    public InventoryProductKnowledge setInventoryProductSync(final String variantId, final InventorySetProductRequest request) {
        CheckoutUtils.validateParams("variantId", variantId, "request", request);
        return apiClient.put(buildPath(INVENTORY_PATH, variantId, PRODUCT_PATH), sdkAuthorization(), InventoryProductKnowledge.class, request);
    }

    @Override
    public EmptyResponse deleteInventoryProductSync(final String variantId) {
        CheckoutUtils.validateParams("variantId", variantId);
        return apiClient.delete(buildPath(INVENTORY_PATH, variantId, PRODUCT_PATH), sdkAuthorization());
    }

}
