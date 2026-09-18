package com.checkout.inventory;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * A monetary amount in the currency's minor units.
 */
@Data
@Builder
public final class InventoryMoney {

    /**
     * The amount in the minor currency unit.
     * [Required]
     */
    @NonNull
    private Long amount;

    /**
     * The three-letter ISO 4217 currency code.
     * [Required]
     * min 3 characters
     * max 3 characters
     */
    @NonNull
    private String currency;

}
