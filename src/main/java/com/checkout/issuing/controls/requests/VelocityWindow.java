package com.checkout.issuing.controls.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VelocityWindow {

    private VelocityWindowType type;
}
