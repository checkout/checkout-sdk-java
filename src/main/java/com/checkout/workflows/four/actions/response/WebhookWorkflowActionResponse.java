package com.checkout.workflows.four.actions.response;

import com.checkout.workflows.four.actions.WebhookSignature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class WebhookWorkflowActionResponse extends WorkflowActionResponse {

    private String url;

    private Map<String, String> headers;

    private WebhookSignature signature;

}
