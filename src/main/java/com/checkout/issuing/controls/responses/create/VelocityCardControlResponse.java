package com.checkout.issuing.controls.responses.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.VelocityLimit;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Card control response for control_type {@code velocity_limit}.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class VelocityCardControlResponse extends CardControlResponse {

    /**
     * The velocity limit, which determines how much a target card can spend over a given timeframe.
     * [Optional] in the update control response, [Required] in the get control response.
     */
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
