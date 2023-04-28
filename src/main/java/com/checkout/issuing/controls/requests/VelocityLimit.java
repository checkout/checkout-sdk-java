package com.checkout.issuing.controls.requests;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VelocityLimit {

    @SerializedName("amount_limit")
    private Integer amountLimit;

    @SerializedName("velocity_window")
    private VelocityWindow velocityWindow;

    @SerializedName("mcc_list")
    private List<String> mccList;
}
