package com.checkout.workflows.events;

import com.checkout.common.Resource;
import com.checkout.workflows.actions.WorkflowActionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EventActionInvocation extends Resource {

    private String workflowId;

    private String workflowActionId;

    private WorkflowActionStatus status;
}
