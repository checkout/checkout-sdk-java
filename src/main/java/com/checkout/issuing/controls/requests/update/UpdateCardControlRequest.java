package com.checkout.issuing.controls.requests.update;

import com.checkout.issuing.controls.requests.MccLimit;
import com.checkout.issuing.controls.requests.MidLimit;
import com.checkout.issuing.controls.requests.VelocityLimit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class UpdateCardControlRequest {

    private String description;

    private VelocityLimit velocityLimit;

    private MccLimit mccLimit;

    private MidLimit midLimit;
}
