package com.checkout.inventory;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * A single variant and quantity within a reservation.
 */
@Data
@Builder
public final class InventoryReservationItem {

    /**
     * The identifier of the variant to hold. The variant must already exist.
     * [Required]
     * max 128 characters
     */
    @NonNull
    private String variantId;

    /**
     * The quantity to hold for this variant.
     * [Required]
     * min 1
     */
    @NonNull
    private Integer quantity;

}
