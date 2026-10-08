package com.checkout.issuing.controls.requests;

import lombok.Builder;
import lombok.Data;

/**
 * The period of time over which the specified {@code amount_limit} can be spent.
 */
@Data
@Builder
public final class VelocityWindow {

    /**
     * The velocity window's unit of time.
     * [Required]
     * Enum: "daily", "weekly", "monthly", "all_time"
     */
    private VelocityWindowType type;
}
