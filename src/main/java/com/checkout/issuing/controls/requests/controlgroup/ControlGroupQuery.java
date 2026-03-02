package com.checkout.issuing.controls.requests.controlgroup;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlGroupQuery {

    @SerializedName("target_id")
    private String targetId;
}