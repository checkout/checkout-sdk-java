package com.checkout.issuing.controls.responses.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MccLimit;
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
public class MccCardControlResponse extends CardControlResponse {

    @SerializedName("mcc_limit")
    private MccLimit mccLimit;

    @Builder
    private MccCardControlResponse(final MccLimit mccLimit) {
        super(ControlType.MCC_LIMIT);
        this.mccLimit = mccLimit;
    }

    public MccCardControlResponse() {
        super(ControlType.VELOCITY_LIMIT);
    }
}
