package com.checkout.workflows.four.conditions.response;

import com.checkout.workflows.four.conditions.WorkflowConditionType;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("processing_channels")
    private List<String> processingChannels;

    public ProcessingChannelWorkflowConditionResponse() {
        super(WorkflowConditionType.PROCESSING_CHANNEL);
    }

}
