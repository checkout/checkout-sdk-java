package com.checkout.webhooks;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("content_type")
    private String contentType;

    @SerializedName("event_types")
    private List<String> eventTypes;

}
