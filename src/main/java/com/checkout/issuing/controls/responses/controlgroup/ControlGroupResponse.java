package com.checkout.issuing.controls.responses.controlgroup;

import com.checkout.common.Resource;
import com.checkout.issuing.controls.requests.controlgroup.ControlGroupControl;
import com.checkout.issuing.controls.requests.controlgroup.ControlGroupFailIf;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("target_id")
    private String targetId;

    @SerializedName("fail_if")
    private ControlGroupFailIf failIf;

    private List<ControlGroupControl> controls;

    @SerializedName("is_editable")
    private Boolean isEditable;

    @SerializedName("created_date")
    private Instant createdDate;

    @SerializedName("last_modified_date")
    private Instant lastModifiedDate;

    private String description;
}