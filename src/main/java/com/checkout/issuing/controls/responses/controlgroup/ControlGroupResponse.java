package com.checkout.issuing.controls.responses.controlgroup;

import com.checkout.common.Resource;
import com.checkout.issuing.controls.requests.controlgroup.ControlGroupControl;
import com.checkout.issuing.controls.requests.controlgroup.ControlGroupFailIf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ControlGroupResponse extends Resource {

    private String id;

    private String targetId;

    private ControlGroupFailIf failIf;

    private List<ControlGroupControl> controls;

    private Boolean isEditable;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private String description;
}