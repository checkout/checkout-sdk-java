package com.checkout.webhooks.previous;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class WebhookResponse extends Resource {

    private String id;

    private String url;

    private boolean active;

    private Map<String, String> headers = new HashMap<>();

    private String contentType;

    private List<String> eventTypes;

}
