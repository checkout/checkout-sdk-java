package com.checkout.webhooks;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WebhookResponse extends Resource {
    private String id;
    private String url;
    private boolean active;
    private Map<String, String> headers;
    private String contentType;
    private List<String> eventTypes = new ArrayList<>();

    public WebhookRequest toRequest() {
        return new WebhookRequest(url, active, headers, contentType, eventTypes);
    }
}