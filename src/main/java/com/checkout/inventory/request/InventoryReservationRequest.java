package com.checkout.inventory.request;

import com.checkout.inventory.InventoryReservationItem;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * The request body for creating an atomic multi-variant hold. All items are reserved together
 * or none are. The hold is protocol-neutral: it is bound to an {@code owner_type} /
 * {@code owner_reference} supplied by the calling protocol adapter (for example, a UCP session
 * or an ACP checkout).
 */
@Data
@Builder
public final class InventoryReservationRequest {

    /**
     * The kind of caller that owns the hold.
     * [Required]
     * max 64 characters
     */
    @NonNull
    private String ownerType;

    /**
     * An opaque reference to the owning session or checkout.
     * [Required]
     * max 256 characters
     */
    @NonNull
    private String ownerReference;

    /**
     * The variants and quantities to hold. {@code variant_id}s must be unique within the request.
     * [Required]
     * min 1 item
     * max 45 items
     */
    @NonNull
    private List<InventoryReservationItem> items;

    /**
     * How long the hold remains valid before it auto-expires. Defaults to {@code 900}.
     * [Optional]
     * min 60
     * max 3600
     */
    private Integer ttlSeconds;

}
