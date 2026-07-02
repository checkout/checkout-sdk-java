package com.checkout.workflows.conditions.response;

import com.checkout.workflows.conditions.WorkflowConditionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ProcessingChannelWorkflowConditionResponse extends WorkflowConditionResponse {

    private List<String> processingChannels;

    public ProcessingChannelWorkflowConditionResponse() {
        super(WorkflowConditionType.PROCESSING_CHANNEL);
    }

}
