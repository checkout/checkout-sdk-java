package com.checkout.workflows.actions.response;

import com.checkout.common.Resource;
import com.checkout.workflows.actions.WorkflowActionStatus;
import com.checkout.workflows.actions.WorkflowActionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class WorkflowActionInvocationsResponse extends Resource {

    private String workflowId;

    private String eventId;

    private String workflowActionId;

    private WorkflowActionType actionType;

    private WorkflowActionStatus status;

    private List<WorkflowActionInvocation> actionInvocations;
}
