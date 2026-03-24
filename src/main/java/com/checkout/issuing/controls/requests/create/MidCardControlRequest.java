package com.checkout.issuing.controls.requests.create;

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
public final class MidCardControlRequest extends CardControlRequest {

    @SerializedName("mid_limit")
    private MidLimit midLimit;

    @Builder
    private MidCardControlRequest(
            final MidLimit midLimit,
            final String description,
            final String targetId
    ) {
        super(ControlType.MID_LIMIT, description, targetId);
        this.midLimit = midLimit;
    }

}