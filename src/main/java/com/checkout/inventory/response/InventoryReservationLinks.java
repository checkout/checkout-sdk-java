package com.checkout.inventory.response;

import com.checkout.inventory.InventoryHalLink;
import lombok.Data;

/**
 * Links to the operations valid for the reservation's current state. A {@code held}
 * reservation exposes {@code self}, {@code commit} and {@code release}; a terminal
 * reservation exposes {@code self} only.
 */
@Data
public final class InventoryReservationLinks {

    /**
     * The link to retrieve the reservation.
     * [Optional]
     */
    private InventoryHalLink self;

    /**
     * The link to commit the reservation. Present only while the reservation is {@code held}.
     * [Optional]
     */
    private InventoryHalLink commit;

    /**
     * The link to release the reservation. Present only while the reservation is {@code held}.
     * [Optional]
     */
    private InventoryHalLink release;

}
