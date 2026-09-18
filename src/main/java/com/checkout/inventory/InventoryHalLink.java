package com.checkout.inventory;

import lombok.Data;

import java.util.List;

/**
 * A HAL link describing a related operation on an inventory resource.
 */
@Data
public final class InventoryHalLink {

    /**
     * Absolute URI of the linked resource.
     * [Optional]
     */
    private String href;

    /**
     * The HTTP methods supported on the linked resource.
     * [Optional]
     */
    private List<String> actions;

    /**
     * The media types supported on the linked resource.
     * [Optional]
     */
    private List<String> types;

}
