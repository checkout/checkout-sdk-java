package com.checkout.issuing.controls.responses.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MccLimit;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Card control response for control_type {@code mcc_limit}.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MccCardControlResponse extends CardControlResponse {

    /**
     * The merchant category code (MCC) rule, which determines the types of businesses transactions can be processed from.
     * [Optional] in the update control response, [Required] in the get control response.
     */
    private MccLimit mccLimit;

    @Builder
    private MccCardControlResponse(final MccLimit mccLimit) {
        super(ControlType.MCC_LIMIT);
        this.mccLimit = mccLimit;
    }

    public MccCardControlResponse() {
        super(ControlType.MCC_LIMIT);
    }
}
