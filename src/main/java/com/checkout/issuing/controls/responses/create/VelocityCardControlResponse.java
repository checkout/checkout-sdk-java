package com.checkout.issuing.controls.responses.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VelocityCardControlResponse extends CardControlResponse {

    @SerializedName("velocity_limit")
    private VelocityLimit velocityLimit;

    @Builder
    private VelocityCardControlResponse(final VelocityLimit velocityLimit) {
        super(ControlType.VELOCITY_LIMIT);
        this.velocityLimit = velocityLimit;
    }

    public VelocityCardControlResponse() {
        super(ControlType.VELOCITY_LIMIT);
    }
}
