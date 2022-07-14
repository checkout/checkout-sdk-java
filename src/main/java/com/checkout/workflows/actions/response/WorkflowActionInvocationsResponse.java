package com.checkout.workflows.actions.response;

import com.checkout.common.Resource;
import com.checkout.workflows.actions.WorkflowActionStatus;
import com.checkout.workflows.actions.WorkflowActionType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class WorkflowActionInvocationsResponse extends Resource {

    @SerializedName("workflow_id")
    private String workflowId;

    @SerializedName("event_id")
    private String eventId;

    @SerializedName("workflow_action_id")
    private String workflowActionId;

    @SerializedName("action_type")
    private WorkflowActionType actionType;

    private WorkflowActionStatus status;

    @SerializedName("action_invocations")
    private List<WorkflowActionInvocation> actionInvocations;
}
