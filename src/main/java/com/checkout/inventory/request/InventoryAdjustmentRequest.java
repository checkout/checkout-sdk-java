package com.checkout.inventory.request;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Beta. The request body for applying a relative adjustment to a variant's on-hand stock.
 */
@Data
@Builder
public final class InventoryAdjustmentRequest {

    /**
     * The identifier of the variant to adjust. The variant must already exist.
     * [Required]
     * max 128 characters
     */
    @NonNull
    private String variantId;

    /**
     * The signed change to apply to {@code on_hand}. Must be non-zero. A negative delta that
     * would drive {@code on_hand} below zero is rejected with {@code 409 conflict}.
     * [Required]
     */
    @NonNull
    private Integer delta;

    /**
     * A required free-text reason recorded in the ledger (for example, damage or found stock).
     * Must not contain personal data.
     * [Required]
     * min 1 character
     * max 256 characters
     */
    @NonNull
    private String reason;

}
