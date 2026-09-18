package com.checkout.inventory;

import com.checkout.EmptyResponse;
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
 * The Inventory client: stock levels, atomic multi-variant reservations, stock adjustments, and
 * per-variant product knowledge for AI agents. Every operation on this client requires the
 * {@code agentic:inventory} OAuth scope; it does not accept a secret or public API key.
 *
 * <p>Error responses ({@code 404}, {@code 409}, {@code 422}) are surfaced the same way as every
 * other domain in this SDK: as a {@link com.checkout.CheckoutApiException} carrying the raw
 * {@code Map<String, Object>} error body, not a typed class (this SDK has no domain that
 * deserializes error bodies into a dedicated type). For the inventory endpoints, that map
 * contains: {@code request_id} (String, for support and correlation), {@code error_type}
 * (String, a high-level classification), {@code error_codes} (array of String, in
 * {@code [subject]_[error]} form), and, present only on an {@code insufficient_stock} conflict,
 * {@code variant_id} (String, the first failing variant) and {@code available} (Integer, its
 * current sellable availability).</p>
 */
public interface InventoryClient {

    /**
     * Applies a relative adjustment to a variant's on-hand stock.
     *
     * @param request the adjustment to apply
     * @return the variant's resulting stock levels
     */
    CompletableFuture<InventoryLevels> adjustInventory(InventoryAdjustmentRequest request);

    /**
     * Applies a relative adjustment to a variant's on-hand stock.
     *
     * @param request        the adjustment to apply
     * @param idempotencyKey an optional idempotency key sent as {@code Cko-Idempotency-Key}
     * @return the variant's resulting stock levels
     */
    CompletableFuture<InventoryLevels> adjustInventory(InventoryAdjustmentRequest request, String idempotencyKey);

    /**
     * Retrieves the current stock levels for a variant.
     *
     * @param variantId the identifier of the variant
     * @return the variant's current stock levels
     */
    CompletableFuture<InventoryLevels> getInventoryLevels(String variantId);

    /**
     * Retrieves the current stock levels for a variant.
     *
     * @param variantId the identifier of the variant
     * @param filter     optional query parameters, for example {@code expand=product}
     * @return the variant's current stock levels
     */
    CompletableFuture<InventoryLevels> getInventoryLevels(String variantId, InventoryLevelsQueryFilter filter);

    /**
     * Sets absolute stock levels for a variant. Creates the inventory item if it does not exist.
     *
     * @param variantId the identifier of the variant
     * @param request    the absolute stock levels to set
     * @return the variant's resulting stock levels
     */
    CompletableFuture<InventoryLevels> setInventoryLevels(String variantId, InventorySetLevelsRequest request);

    /**
     * Creates an atomic multi-variant stock hold.
     *
     * @param request the reservation to create
     * @return the created reservation
     */
    CompletableFuture<InventoryReservation> createInventoryReservation(InventoryReservationRequest request);

    /**
     * Creates an atomic multi-variant stock hold.
     *
     * @param request        the reservation to create
     * @param idempotencyKey an optional idempotency key sent as {@code Cko-Idempotency-Key}
     * @return the created reservation
     */
    CompletableFuture<InventoryReservation> createInventoryReservation(InventoryReservationRequest request, String idempotencyKey);

    /**
     * Retrieves a reservation.
     *
     * @param reservationId the identifier of the reservation
     * @return the reservation
     */
    CompletableFuture<InventoryReservation> getInventoryReservation(String reservationId);

    /**
     * Commits a held reservation, converting the hold into a permanent stock deduction.
     *
     * @param reservationId the identifier of the reservation
     * @return the committed reservation
     */
    CompletableFuture<InventoryReservation> commitInventoryReservation(String reservationId);

    /**
     * Releases a held reservation, returning the held quantities to available stock.
     *
     * @param reservationId the identifier of the reservation
     * @return the released reservation
     */
    CompletableFuture<InventoryReservation> releaseInventoryReservation(String reservationId);

    /**
     * Beta. Retrieves the product knowledge for a variant.
     *
     * @param variantId the identifier of the variant
     * @return the variant's product knowledge
     */
    CompletableFuture<InventoryProductKnowledge> getInventoryProduct(String variantId);

    /**
     * Beta. Sets (upserts) the product knowledge for a variant.
     *
     * @param variantId the identifier of the variant
     * @param request    the product knowledge to set
     * @return the resulting product knowledge
     */
    CompletableFuture<InventoryProductKnowledge> setInventoryProduct(String variantId, InventorySetProductRequest request);

    /**
     * Beta. Deletes the product knowledge for a variant.
     *
     * @param variantId the identifier of the variant
     * @return an empty response
     */
    CompletableFuture<EmptyResponse> deleteInventoryProduct(String variantId);

    // Synchronous methods

    InventoryLevels adjustInventorySync(InventoryAdjustmentRequest request);

    InventoryLevels adjustInventorySync(InventoryAdjustmentRequest request, String idempotencyKey);

    InventoryLevels getInventoryLevelsSync(String variantId);

    InventoryLevels getInventoryLevelsSync(String variantId, InventoryLevelsQueryFilter filter);

    InventoryLevels setInventoryLevelsSync(String variantId, InventorySetLevelsRequest request);

    InventoryReservation createInventoryReservationSync(InventoryReservationRequest request);

    InventoryReservation createInventoryReservationSync(InventoryReservationRequest request, String idempotencyKey);

    InventoryReservation getInventoryReservationSync(String reservationId);

    InventoryReservation commitInventoryReservationSync(String reservationId);

    InventoryReservation releaseInventoryReservationSync(String reservationId);

    InventoryProductKnowledge getInventoryProductSync(String variantId);

    InventoryProductKnowledge setInventoryProductSync(String variantId, InventorySetProductRequest request);

    EmptyResponse deleteInventoryProductSync(String variantId);

}
