package com.checkout.workflows.four.actions.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
public class WorkflowActionInvocation {

    @SerializedName("invocation_id")
    private String invocationId;

    private Timestamp timestamp;

    private Boolean retry;

    private Boolean succeeded;

    @SerializedName("final")
    private Boolean finalAttempt;

    private Map<String, Object> result;
}
