package com.checkout.workflows.actions.response;

import com.checkout.workflows.actions.WorkflowActionType;
import lombok.Data;

@Data
public abstract class WorkflowActionResponse {

    private WorkflowActionType type;

    private String id;

}
