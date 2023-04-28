package com.checkout.issuing.controls.requests.create;

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
public class MccCardControlRequest extends AbstractCardControlRequest {

    @SerializedName("mcc_limit")
    private MccLimit mccLimit;

    @Builder
    public MccCardControlRequest(
            MccLimit mccLimit,
            String description,
            String targetId
    ) {
        super(ControlType.MCC_LIMIT, description, targetId);
        this.mccLimit = mccLimit;
    }

    public MccCardControlRequest() {
        super(ControlType.MCC_LIMIT);
    }
}
