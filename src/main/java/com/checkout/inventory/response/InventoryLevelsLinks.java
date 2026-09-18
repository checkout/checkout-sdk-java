package com.checkout.inventory.response;

import com.checkout.inventory.InventoryHalLink;
import lombok.Data;

/**
 * Links to related operations on the variant.
 */
@Data
public final class InventoryLevelsLinks {

    /**
     * The link to retrieve the current stock levels for the variant.
     * [Optional]
     */
    private InventoryHalLink self;

    /**
     * The link to set absolute stock levels for the variant.
     * [Optional]
     */
    private InventoryHalLink set;

}
