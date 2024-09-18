package com.checkout.workflows;

import com.checkout.workflows.actions.request.WorkflowActionRequest;
import com.checkout.workflows.conditions.request.WorkflowConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public final class UpdateWorkflowRequest {

    private String name;

    private Boolean active;

    @Builder.Default
    private List<WorkflowConditionRequest> conditions = new ArrayList<>();

    @Builder.Default
    private List<WorkflowActionRequest> actions = new ArrayList<>();

}
