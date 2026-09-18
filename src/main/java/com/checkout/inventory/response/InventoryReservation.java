package com.checkout.inventory.response;

import com.checkout.HttpMetadata;
import com.checkout.inventory.InventoryReservationItem;
import com.checkout.inventory.InventoryReservationState;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

/**
 * A stock hold and its current lifecycle state. Returned by createInventoryReservation (both
 * the {@code 200} idempotent replay and the {@code 201} created responses share this schema),
 * getInventoryReservation, commitInventoryReservation and releaseInventoryReservation.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class InventoryReservation extends HttpMetadata {

    /**
     * The reservation identifier, in the {@code rsv_{base32-encoded GUID}} format.
     * [Optional]
     */
    private String id;

    /**
     * The current state of the hold. A {@code held} reservation past its {@code expires_at} is
     * reported as {@code expired}. Transitions out of {@code held} are terminal.
     * [Optional]
     * Enum: "held" "committed" "released" "expired"
     */
    private InventoryReservationState state;

    /**
     * Echo of the owning caller kind.
     * [Optional]
     */
    private String ownerType;

    /**
     * Echo of the owning session or checkout reference.
     * [Optional]
     */
    private String ownerReference;

    /**
     * The variants and quantities held by this reservation.
     * [Optional]
     */
    private List<InventoryReservationItem> items;

    /**
     * The server-authoritative time at which the hold expires.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant expiresAt;

    /**
     * The date and time the reservation was created.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant createdOn;

    /**
     * Links to the operations valid for the reservation's current state. A {@code held}
     * reservation exposes {@code self}, {@code commit} and {@code release}; a terminal
     * reservation exposes {@code self} only.
     * [Optional]
     */
    @SerializedName("_links")
    private InventoryReservationLinks links;

}
