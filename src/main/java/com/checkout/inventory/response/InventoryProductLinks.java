package com.checkout.inventory.response;

import com.checkout.inventory.InventoryHalLink;
import lombok.Data;

/**
 * Links to related operations on the variant's product knowledge.
 */
@Data
public final class InventoryProductLinks {

    /**
     * The link to retrieve the product knowledge for the variant.
     * [Optional]
     */
    private InventoryHalLink self;

    /**
     * The link to set the product knowledge for the variant.
     * [Optional]
     */
    private InventoryHalLink set;

    /**
     * The link to delete the product knowledge for the variant.
     * [Optional]
     */
    private InventoryHalLink delete;

}
