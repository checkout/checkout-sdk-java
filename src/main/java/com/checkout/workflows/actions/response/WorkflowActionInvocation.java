package com.checkout.workflows.actions.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
public final class WorkflowActionInvocation {

    @SerializedName("invocation_id")
    private String invocationId;

    private Timestamp timestamp;

    private Boolean retry;

    private Boolean succeeded;

    @SerializedName("final")
    private Boolean finalAttempt;

    @SerializedName("result_details")
    private Map<String, Object> result;
}
