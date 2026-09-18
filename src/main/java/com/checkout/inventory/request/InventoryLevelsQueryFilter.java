package com.checkout.inventory.request;

import lombok.Builder;
import lombok.Data;

/**
 * Optional query parameters for retrieving a variant's stock levels.
 */
@Data
@Builder
public final class InventoryLevelsQueryFilter {

    /**
     * When set to {@code product}, embeds the variant's product knowledge in the response
     * {@code product} field, if it exists.
     * [Optional]
     */
    private String expand;

}
