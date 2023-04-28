package com.checkout.issuing.controls.requests.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractCardControlRequest {

    private final ControlType type;
    private String description;

    @SerializedName("target_id")
    private String targetId;

    protected AbstractCardControlRequest(final ControlType type) {
        this.type = type;
    }
}
