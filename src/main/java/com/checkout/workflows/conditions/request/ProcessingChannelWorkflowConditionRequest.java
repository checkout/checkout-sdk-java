package com.checkout.workflows.conditions.request;

import com.checkout.workflows.conditions.WorkflowConditionType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ProcessingChannelWorkflowConditionRequest extends WorkflowConditionRequest {

    private List<String> processingChannels;

    public ProcessingChannelWorkflowConditionRequest() {
        super(WorkflowConditionType.PROCESSING_CHANNEL);
    }

    @Builder
    private ProcessingChannelWorkflowConditionRequest(final List<String> processingChannels) {
        super(WorkflowConditionType.PROCESSING_CHANNEL);
        this.processingChannels = processingChannels;
    }

}

