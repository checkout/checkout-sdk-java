package com.checkout.issuing.controls.responses.controlgroup;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ControlGroupsQueryResponse extends HttpMetadata {

    @SerializedName("control_groups")
    private List<ControlGroupResponse> controlGroups;
}