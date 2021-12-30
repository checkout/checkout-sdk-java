package com.checkout.workflows.four.conditions.request;

import com.checkout.workflows.four.conditions.WorkflowConditionType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ProcessingChannelWorkflowConditionRequest extends WorkflowConditionRequest {

    @SerializedName("processing_channels")
    private List<String> processingChannels;

    public ProcessingChannelWorkflowConditionRequest() {
        super(WorkflowConditionType.PROCESSING_CHANNEL);
    }

    @Builder
    public ProcessingChannelWorkflowConditionRequest(final List<String> processingChannels) {
        super(WorkflowConditionType.PROCESSING_CHANNEL);
        this.processingChannels = processingChannels;
    }

}

