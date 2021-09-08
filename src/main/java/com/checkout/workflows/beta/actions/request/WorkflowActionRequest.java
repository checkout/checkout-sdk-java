package com.checkout.workflows.beta.actions.request;

import com.checkout.workflows.beta.actions.WorkflowActionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class WorkflowActionRequest {

    private WorkflowActionType type;

    public WorkflowActionRequest(final WorkflowActionType type) {
        this.type = type;
    }

}
