package com.checkout.workflows;

import com.checkout.common.Resource;
import com.checkout.workflows.actions.response.WorkflowActionResponse;
import com.checkout.workflows.conditions.response.WorkflowConditionResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateWorkflowResponse extends Resource {

    private String id;

    private String name;

    private Boolean active;

    private List<WorkflowConditionResponse> conditions = new ArrayList<>();

    private List<WorkflowActionResponse> actions = new ArrayList<>();

}
