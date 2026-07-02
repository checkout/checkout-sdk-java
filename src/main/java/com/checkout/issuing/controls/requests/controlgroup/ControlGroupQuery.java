package com.checkout.issuing.controls.requests.controlgroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ControlGroupQuery {

    private String targetId;
}