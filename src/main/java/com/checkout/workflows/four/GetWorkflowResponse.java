package com.checkout.workflows.four;

import com.checkout.common.Resource;
import com.checkout.workflows.four.actions.response.WorkflowActionResponse;
import com.checkout.workflows.four.conditions.response.WorkflowConditionResponse;
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

    private Boolean active;

    private List<WorkflowConditionResponse> conditions = new ArrayList<>();

    private List<WorkflowActionResponse> actions = new ArrayList<>();

}
