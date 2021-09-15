package com.checkout.workflows.four.actions.response;

import com.checkout.workflows.four.actions.WorkflowActionType;
import lombok.Data;

@Data
public abstract class WorkflowActionResponse {

    private WorkflowActionType type;

    private String id;

}
