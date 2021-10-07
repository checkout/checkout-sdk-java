package com.checkout.webhooks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class WebhookRequest {

    @NonNull
    private String url;

    @Builder.Default
    private boolean active = true;

    private Map<String, String> headers;

    private String contentType;

    @Builder.Default
    private List<String> eventTypes = new ArrayList<>();

    /**
     * @deprecated Will be removed in a future version.
     */
    @Deprecated
    public WebhookRequest(final String url, final List<String> eventTypes) {
        this(url, true, new HashMap<>(), "json", eventTypes);
    }

}
