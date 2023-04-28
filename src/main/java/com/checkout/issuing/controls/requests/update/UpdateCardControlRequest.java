package com.checkout.issuing.controls.requests.update;

import com.checkout.issuing.controls.requests.MccLimit;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCardControlRequest {

    private String description;

    @SerializedName("velocity_limit")
    private VelocityLimit velocityLimit;

    @SerializedName("mcc_limit")
    private MccLimit mccLimit;
}
