package com.checkout.issuing.controls.responses.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MccLimit;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MccCardControlResponse extends AbstractCardControlResponse {

    @SerializedName("mcc_limit")
    private MccLimit mccLimit;

    public MccCardControlResponse(ControlType controlType) {
        super(controlType);
    }

    public MccCardControlResponse(MccLimit mccLimit) {
        super(ControlType.MCC_LIMIT);
        this.mccLimit = mccLimit;
    }
}
