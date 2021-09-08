package com.checkout.workflows.beta;

import com.checkout.common.Resource;
import com.checkout.workflows.beta.actions.response.WorkflowActionResponse;
import com.checkout.workflows.beta.conditions.response.WorkflowConditionResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetWorkflowResponse extends Resource {

    private String id;

    private String name;

    private List<WorkflowConditionResponse> conditions = new ArrayList<>();

    private List<WorkflowActionResponse> actions = new ArrayList<>();

}
