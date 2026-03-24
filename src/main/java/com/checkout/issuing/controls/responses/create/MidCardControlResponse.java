package com.checkout.issuing.controls.responses.create;

import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MidLimit;
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
public final class MidCardControlResponse extends CardControlResponse {

    @SerializedName("mid_limit")
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