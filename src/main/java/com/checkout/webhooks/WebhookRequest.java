package com.checkout.webhooks;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebhookRequest {
    @NonNull
    private String url;
    @Builder.Default
    private boolean active = true;
    private Map<String, String> headers;
    private String contentType;
    @NonNull
    private List<String> eventTypes;

    public WebhookRequest(String url, List<String> eventTypes) {
        this(url, true, new HashMap<>(), "json", eventTypes);
    }
}
