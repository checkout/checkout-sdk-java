package com.checkout.issuing.controls.requests;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MccLimit {

    private MccControlType type;

    @SerializedName("mcc_list")
    private List<String> mccList;
}
