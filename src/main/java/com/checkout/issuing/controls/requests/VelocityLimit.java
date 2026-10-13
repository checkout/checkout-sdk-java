package com.checkout.issuing.controls.requests;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * The velocity limit, which determines how much a target card can spend over a given timeframe.
 * <p>
 * Used in control requests and, as {@code VelocityLimitWithRemainingAmount}, in control responses.
 */
@Data
public final class VelocityLimit {

    /**
     * The amount that can be spent, in minor units.
     * [Required]
     * int64, minimum 0
     */
    private Integer amountLimit;

    /**
     * The period of time over which the specified {@code amount_limit} can be spent.
     * [Required]
     */
    private VelocityWindow velocityWindow;

    /**
     * The list of merchant category codes (MCCs) that the velocity limit applies to, as four-digit ISO 18245 codes.
     * [Optional]
     */
    private List<String> mccList;

    /**
     * The list of merchant identification (MID) codes to allow or block transactions from.
     * You can provide either {@code mcc_list} or {@code mid_list}, but not both.
     * [Optional]
     */
    private List<String> midList;

    /**
     * The remaining amount that can be spent, in minor units.
     * [Required] in control responses only. It is read from the API response and is never sent on requests:
     * it has no builder method and no setter, so it is always null on request bodies.
     * int64, minimum 0
     */
    @Setter(AccessLevel.NONE)
    private Long amountRemaining;

    @Builder
    private VelocityLimit(final Integer amountLimit,
                          final VelocityWindow velocityWindow,
                          final List<String> mccList,
                          final List<String> midList) {
        this.amountLimit = amountLimit;
        this.velocityWindow = velocityWindow;
        this.mccList = mccList;
        this.midList = midList;
    }
}
