package com.checkout.workflows.events;

import com.checkout.common.Resource;
import com.checkout.workflows.actions.WorkflowActionStatus;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EventActionInvocation extends Resource {

    @SerializedName("workflow_id")
    private String workflowId;

    @SerializedName("workflow_action_id")
    private String workflowActionId;

    private WorkflowActionStatus status;
}
