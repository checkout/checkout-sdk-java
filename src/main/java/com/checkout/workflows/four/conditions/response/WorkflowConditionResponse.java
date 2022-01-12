package com.checkout.workflows.four.conditions.response;

import com.checkout.common.Resource;
import com.checkout.workflows.four.conditions.WorkflowConditionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class WorkflowConditionResponse extends Resource {

    private final WorkflowConditionType type;

    private String id;

    public WorkflowConditionResponse(final WorkflowConditionType type) {
        this.type = type;
    }

}
