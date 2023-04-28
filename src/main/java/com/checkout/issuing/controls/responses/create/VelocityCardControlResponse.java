package com.checkout.issuing.controls.responses.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VelocityCardControlResponse extends AbstractCardControlResponse {

    @SerializedName("velocity_limit")
    private VelocityLimit velocityLimit;

    public VelocityCardControlResponse(VelocityLimit velocityLimit) {
        super(ControlType.VELOCITY_LIMIT);
        this.velocityLimit = velocityLimit;
    }
}
