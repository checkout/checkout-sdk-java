package com.checkout.workflows.four.actions.request;

import com.checkout.workflows.four.actions.WorkflowActionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class WorkflowActionRequest {

    private WorkflowActionType type;

    protected WorkflowActionRequest(final WorkflowActionType type) {
        this.type = type;
    }

}
