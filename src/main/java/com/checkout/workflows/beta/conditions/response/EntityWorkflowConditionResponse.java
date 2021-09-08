package com.checkout.workflows.beta.conditions.response;

import com.checkout.workflows.beta.conditions.WorkflowConditionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EntityWorkflowConditionResponse extends WorkflowConditionResponse {

    private List<String> entities;

    public EntityWorkflowConditionResponse() {
        super(WorkflowConditionType.ENTITY);
    }

}
