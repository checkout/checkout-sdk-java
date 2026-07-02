package com.checkout.issuing.controls.requests.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MccLimit;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MccCardControlRequest extends CardControlRequest {

    private MccLimit mccLimit;

    @Builder
    private MccCardControlRequest(
            final MccLimit mccLimit,
            final String description,
            final String targetId
    ) {
        super(ControlType.MCC_LIMIT, description, targetId);
        this.mccLimit = mccLimit;
    }

}
