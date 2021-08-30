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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * Will be removed in a future version.
     */
    @Deprecated
    public WebhookRequest(final String url, final List<String> eventTypes) {
        this(url, true, new HashMap<>(), "json", eventTypes);
    }

    /**
     * Added with the deprecation of {@link WebhookRequest} constructor
     * to serve as a utility method to set event types.
     * <p>
     * Will be removed in a future version.
     */
    @Deprecated
    public void addEventTypes(final EventType... eventTypes) {
        this.eventTypes.addAll(Stream.of(eventTypes).map(EventType::getCode).collect(Collectors.toList()));
    }

}
