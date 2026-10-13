package com.checkout.issuing.controls.responses.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MidLimit;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Card control response for control_type {@code mid_limit}.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MidCardControlResponse extends CardControlResponse {

    /**
     * The merchant identification (MID) code rule, which determines the merchants from whom transactions can be processed.
     * [Optional] in the update control response, [Required] in the get control response.
     */
    private MidLimit midLimit;

    @Builder
    private MidCardControlResponse(final MidLimit midLimit) {
        super(ControlType.MID_LIMIT);
        this.midLimit = midLimit;
    }

    public MidCardControlResponse() {
        super(ControlType.MID_LIMIT);
    }
}