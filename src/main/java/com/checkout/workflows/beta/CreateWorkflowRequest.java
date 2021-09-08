package com.checkout.workflows.beta;

import com.checkout.workflows.beta.actions.request.WorkflowActionRequest;
import com.checkout.workflows.beta.conditions.request.WorkflowConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public final class CreateWorkflowRequest {

    private String name;

    @Builder.Default
    private List<WorkflowConditionRequest> conditions = new ArrayList<>();

    @Builder.Default
    private List<WorkflowActionRequest> actions = new ArrayList<>();

}
