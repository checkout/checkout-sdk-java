package com.checkout.workflows.beta.conditions.request;

import com.checkout.workflows.beta.conditions.WorkflowConditionType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EntityWorkflowConditionRequest extends WorkflowConditionRequest {

    private List<String> entities;

    public EntityWorkflowConditionRequest() {
        super(WorkflowConditionType.ENTITY);
    }

    @Builder
    public EntityWorkflowConditionRequest(final List<String> entities) {
        super(WorkflowConditionType.ENTITY);
        this.entities = entities;
    }

}

