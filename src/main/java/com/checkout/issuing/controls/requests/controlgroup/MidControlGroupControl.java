package com.checkout.issuing.controls.requests.controlgroup;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MidLimit;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Control group control that applies MID (Merchant Identifier) limits to restrict or allow transactions at specific merchants.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MidControlGroupControl extends ControlGroupControl {

    /**
     * The MID limit configuration that defines which merchant identifiers to block or allow.
     * [Required]
     */
    @SerializedName("mid_limit")
    private MidLimit midLimit;

    @Builder
    private MidControlGroupControl(
            final MidLimit midLimit,
            final String description
    ) {
        super(ControlType.MID_LIMIT, description);
        this.midLimit = midLimit;
    }

    /**
     * Default constructor for JSON deserialization.
     */
    public MidControlGroupControl() {
        super(ControlType.MID_LIMIT);
    }
}