package com.checkout.apm.klarna;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class OrderCaptureResponse {

    @SerializedName("action_id")
    private String actionId;

}
