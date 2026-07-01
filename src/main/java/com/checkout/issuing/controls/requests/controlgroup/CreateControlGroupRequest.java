package com.checkout.issuing.controls.requests.controlgroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateControlGroupRequest {

    private String targetId;

    private ControlGroupFailIf failIf;

    private List<ControlGroupControl> controls;

    private String description;
}