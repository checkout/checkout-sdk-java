package com.checkout.workflows.beta.conditions.response;

import com.checkout.workflows.beta.conditions.WorkflowConditionType;
import lombok.Data;

@Data
public abstract class WorkflowConditionResponse {

    private final WorkflowConditionType type;

    private String id;

    public WorkflowConditionResponse(final WorkflowConditionType type) {
        this.type = type;
    }

}
