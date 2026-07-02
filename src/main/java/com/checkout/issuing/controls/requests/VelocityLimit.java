package com.checkout.issuing.controls.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class VelocityLimit {

    private Integer amountLimit;

    private VelocityWindow velocityWindow;

    private List<String> mccList;

    private List<String> midList;
}
