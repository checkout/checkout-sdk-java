package com.checkout.issuing.controls.requests.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class CardControlRequest {

    @SerializedName("control_type")
    private final ControlType type;

    private String description;

    @SerializedName("target_id")
    private String targetId;

    protected CardControlRequest(final ControlType type,
                                 final String description,
                                 final String targetId) {
        this.type = type;
        this.description = description;
        this.targetId = targetId;
    }
}
