package com.checkout.issuing.controls.requests.controlgroup;

import com.checkout.issuing.controls.requests.ControlType;

/**
 * Mapping configuration for control group control types to their corresponding classes and JSON property names.
 */
public enum ControlGroupControlTypeMapping {
    
    MCC_LIMIT(ControlType.MCC_LIMIT, "mcc_limit", MccControlGroupControl.class),
    MID_LIMIT(ControlType.MID_LIMIT, "mid_limit", MidControlGroupControl.class),
    VELOCITY_LIMIT(ControlType.VELOCITY_LIMIT, "velocity_limit", VelocityControlGroupControl.class);
    
    private final ControlType controlType;
    private final String jsonProperty;
    private final Class<? extends ControlGroupControl> controlClass;
    
    ControlGroupControlTypeMapping(ControlType controlType, String jsonProperty, Class<? extends ControlGroupControl> controlClass) {
        this.controlType = controlType;
        this.jsonProperty = jsonProperty;
        this.controlClass = controlClass;
    }
    
    public ControlType getControlType() {
        return controlType;
    }
    
    public String getJsonProperty() {
        return jsonProperty;
    }
    
    public Class<? extends ControlGroupControl> getControlClass() {
        return controlClass;
    }
    
    /**
     * Find mapping by JSON property name.
     */
    public static ControlGroupControlTypeMapping findByJsonProperty(String jsonProperty) {
        for (ControlGroupControlTypeMapping mapping : values()) {
            if (mapping.getJsonProperty().equals(jsonProperty)) {
                return mapping;
            }
        }
        return null;
    }
    
    /**
     * Find mapping by control type.
     */
    public static ControlGroupControlTypeMapping findByControlType(ControlType controlType) {
        for (ControlGroupControlTypeMapping mapping : values()) {
            if (mapping.getControlType() == controlType) {
                return mapping;
            }
        }
        return null;
    }
}