package com.checkout.workflows.events;

import com.checkout.common.Resource;
import com.checkout.workflows.actions.response.WorkflowActionInvocation;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetEventResponse extends Resource {

    private String id;

    private String source;

    private String type;

    private String timestamp;

    private String version;

    private Map<String, Object> data;

    @SerializedName("action_invocations")
    private List<WorkflowActionInvocation> actionInvocations;

}
