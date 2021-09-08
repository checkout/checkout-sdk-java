package com.checkout.workflows.beta.reflow;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class ReflowResponse {

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("error_type")
    private String errorType;

    @SerializedName("error_codes")
    private List<String> errorCodes;

}
