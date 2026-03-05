package com.checkout.issuing.controls.requests.controlgroup;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Control group control that applies velocity limits to restrict transaction frequency and amounts over time periods.
 * Note: Control profiles cannot be a target for velocity_limit controls.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VelocityControlGroupControl extends ControlGroupControl {

    /**
     * The velocity limit configuration that defines transaction frequency and amount restrictions over time periods.
     * [Required]
     */
    @SerializedName("velocity_limit")
    private VelocityLimit velocityLimit;

    @Builder
    private VelocityControlGroupControl(
            final VelocityLimit velocityLimit,
            final String description
    ) {
        super(ControlType.VELOCITY_LIMIT, description);
        this.velocityLimit = velocityLimit;
    }

    /**
     * Default constructor for JSON deserialization.
     */
    public VelocityControlGroupControl() {
        super(ControlType.VELOCITY_LIMIT);
    }
}