package com.checkout.issuing.controls.requests.controlgroup;

import com.checkout.issuing.controls.requests.ControlType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Base class for control group controls that define restrictions or permissions for card usage.
 */
@Data
public abstract class ControlGroupControl {

    /**
     * The type of control being applied.
     * Enum: "mcc_limit" "mid_limit" "velocity_limit"
     * [Required]
     */
    @SerializedName("control_type")
    private final ControlType controlType;

    /**
     * A description of the control.
     * [Optional]
     */
    private String description;

    /**
     * Initializes a new instance of the ControlGroupControl class with the specified control type.
     * 
     * @param controlType The type of control
     * @param description A description of the control
     */
    protected ControlGroupControl(ControlType controlType, String description) {
        this.controlType = controlType;
        this.description = description;
    }

    /**
     * Initializes a new instance of the ControlGroupControl class with the specified control type.
     * 
     * @param controlType The type of control
     */
    protected ControlGroupControl(ControlType controlType) {
        this(controlType, null);
    }
}