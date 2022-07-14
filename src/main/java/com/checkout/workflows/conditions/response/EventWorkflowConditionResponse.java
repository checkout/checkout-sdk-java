package com.checkout.workflows.conditions.response;

import com.checkout.workflows.conditions.WorkflowConditionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EventWorkflowConditionResponse extends WorkflowConditionResponse {

    private Map<String, Set<String>> events;

    public EventWorkflowConditionResponse() {
        super(WorkflowConditionType.EVENT);
    }

}
