package com.checkout.workflows.beta.actions.response;

import com.checkout.workflows.beta.actions.WorkflowActionType;
import lombok.Data;

@Data
public abstract class WorkflowActionResponse {

    private WorkflowActionType type;

    private String id;

}
