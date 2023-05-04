package com.checkout.issuing.controls.requests.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VelocityCardControlRequest extends CardControlRequest {

    @SerializedName("velocity_limit")
    private VelocityLimit velocityLimit;

    @Builder
    private VelocityCardControlRequest(
            final VelocityLimit velocityLimit,
            final String description,
            final String targetId
    ) {
        super(ControlType.VELOCITY_LIMIT, description, targetId);
        this.velocityLimit = velocityLimit;
    }
}
