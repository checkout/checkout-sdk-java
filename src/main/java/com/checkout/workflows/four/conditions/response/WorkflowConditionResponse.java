package com.checkout.workflows.four.conditions.response;

import com.checkout.workflows.four.conditions.WorkflowConditionType;
import lombok.Data;

@Data
public abstract class WorkflowConditionResponse {

    private final WorkflowConditionType type;

    private String id;

    public WorkflowConditionResponse(final WorkflowConditionType type) {
        this.type = type;
    }

}
