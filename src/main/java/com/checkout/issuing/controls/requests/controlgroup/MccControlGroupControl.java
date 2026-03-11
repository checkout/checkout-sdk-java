package com.checkout.issuing.controls.requests.controlgroup;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MccLimit;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Control group control that applies MCC (Merchant Category Code) limits to restrict or allow transactions based on merchant categories.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MccControlGroupControl extends ControlGroupControl {

    /**
     * The MCC limit configuration that defines which merchant category codes to block or allow.
     * [Required]
     */
    @SerializedName("mcc_limit")
    private MccLimit mccLimit;

    @Builder
    private MccControlGroupControl(
            final MccLimit mccLimit,
            final String description
    ) {
        super(ControlType.MCC_LIMIT, description);
        this.mccLimit = mccLimit;
    }

    /**
     * Default constructor for JSON deserialization.
     */
    public MccControlGroupControl() {
        super(ControlType.MCC_LIMIT);
    }
}