package com.checkout.issuing.controls.responses.controlgroup;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ControlGroupsQueryResponse extends HttpMetadata {

    private List<ControlGroupResponse> controlGroups;
}