package com.checkout.issuing.controls.requests.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractCardControlRequest {

    @SerializedName("control_type")
    private final ControlType controlType;

    private String description;

    @SerializedName("target_id")
    private String targetId;

    protected AbstractCardControlRequest(final ControlType controlType) {
        this.controlType = controlType;
    }
}
