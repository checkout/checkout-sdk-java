package com.checkout.workflows.four.conditions.response;

import com.checkout.common.Resource;
import com.checkout.workflows.four.conditions.WorkflowConditionType;
import lombok.Data;

@Data
public abstract class WorkflowConditionResponse extends Resource {

    private final WorkflowConditionType type;

    private String id;

    public WorkflowConditionResponse(final WorkflowConditionType type) {
        this.type = type;
    }

}
