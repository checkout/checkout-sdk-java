package com.checkout.workflows.reflow;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class ReflowResponse extends HttpMetadata {

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("error_type")
    private String errorType;

    @SerializedName("error_codes")
    private List<String> errorCodes;

}
