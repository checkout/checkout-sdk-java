package com.checkout.inventory.response;

import com.checkout.HttpMetadata;
import com.checkout.inventory.InventoryLevelsSource;
import com.checkout.inventory.InventoryLevelsState;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * The current stock levels and sellable availability for a single variant. Returned by
 * getInventoryLevels, setInventoryLevels and adjustInventory (both the {@code 200} idempotent
 * replay and the {@code 201} created responses share this schema).
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class InventoryLevels extends HttpMetadata {

    /**
     * The merchant-provided identifier for the variant.
     * [Optional]
     */
    private String variantId;

    /**
     * Physical stock known to Checkout.com.
     * [Optional]
     */
    private Integer onHand;

    /**
     * The sum of all currently active holds against this variant.
     * [Optional]
     */
    private Integer reserved;

    /**
     * A buffer quantity withheld from sale.
     * [Optional]
     */
    private Integer safetyStock;

    /**
     * The sellable quantity, clamped to zero: {@code max(0, on_hand - reserved - safety_stock)}.
     * This is the value an availability feed should publish.
     * [Optional]
     */
    private Integer available;

    /**
     * A derived availability state for the variant.
     * [Optional]
     * Enum: "in_stock" "limited" "out_of_stock"
     */
    private InventoryLevelsState state;

    /**
     * How the levels are maintained. Always {@code managed} in the current version.
     * [Optional]
     * Enum: "managed" "sync"
     */
    private InventoryLevelsSource source;

    /**
     * The date and time the inventory item was created.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant createdOn;

    /**
     * The date and time the inventory item was last modified.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant modifiedOn;

    /**
     * The variant's product knowledge, embedded when {@code expand=product} is passed on the
     * request and product knowledge exists for the variant. Omitted otherwise.
     * [Optional]
     */
    private InventoryProductKnowledge product;

    /**
     * Links to related operations on the variant.
     * [Optional]
     */
    @SerializedName("_links")
    private InventoryLevelsLinks links;

}
