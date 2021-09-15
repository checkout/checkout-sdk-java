package com.checkout.workflows.four.conditions.response;

import com.checkout.workflows.four.conditions.WorkflowConditionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EventWorkflowConditionResponse extends WorkflowConditionResponse {

    private Map<String, Set<String>> events;

    public EventWorkflowConditionResponse() {
        super(WorkflowConditionType.EVENT);
    }

}
