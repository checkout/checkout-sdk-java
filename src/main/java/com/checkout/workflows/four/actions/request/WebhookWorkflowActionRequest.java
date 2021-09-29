package com.checkout.workflows.four.actions.request;

import com.checkout.workflows.four.actions.WebhookSignature;
import com.checkout.workflows.four.actions.WorkflowActionType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class WebhookWorkflowActionRequest extends WorkflowActionRequest {

    private String url;

    private Map<String, String> headers;

    private WebhookSignature signature;

    public WebhookWorkflowActionRequest() {
        super(WorkflowActionType.WEBHOOK);
    }

    @Builder
    public WebhookWorkflowActionRequest(final String url,
                                        final Map<String, String> headers,
                                        final WebhookSignature signature) {
        super(WorkflowActionType.WEBHOOK);
        this.url = url;
        this.headers = headers;
        this.signature = signature;
    }

}
