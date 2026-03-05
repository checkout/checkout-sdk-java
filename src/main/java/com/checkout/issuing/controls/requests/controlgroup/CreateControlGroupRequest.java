package com.checkout.issuing.controls.requests.controlgroup;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateControlGroupRequest {

    @SerializedName("target_id")
    private String targetId;

    @SerializedName("fail_if")
    private ControlGroupFailIf failIf;

    private List<ControlGroupControl> controls;

    private String description;
}