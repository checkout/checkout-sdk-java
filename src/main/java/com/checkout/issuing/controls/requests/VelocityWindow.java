package com.checkout.issuing.controls.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class VelocityWindow {

    private VelocityWindowType type;
}
