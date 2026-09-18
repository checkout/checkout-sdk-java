package com.checkout.inventory.request;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * The request body for setting absolute stock levels on a variant.
 */
@Data
@Builder
public final class InventorySetLevelsRequest {

    /**
     * The absolute physical stock to set for the variant.
     * [Required]
     * min 0
     */
    @NonNull
    private Integer onHand;

    /**
     * The buffer quantity to withhold from sale. Defaults to {@code 0} when the item is
     * created and is left unchanged on update if omitted.
     * [Optional]
     * min 0
     */
    private Integer safetyStock;

    /**
     * An optional free-text reason recorded in the ledger. Must not contain personal data.
     * [Optional]
     * max 256 characters
     */
    private String reason;

}
