package com.checkout.workflows.four;

import com.checkout.workflows.four.actions.request.WorkflowActionRequest;
import com.checkout.workflows.four.conditions.request.WorkflowConditionRequest;
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

    private Boolean active;

    @Builder.Default
    private List<WorkflowConditionRequest> conditions = new ArrayList<>();

    @Builder.Default
    private List<WorkflowActionRequest> actions = new ArrayList<>();

}
