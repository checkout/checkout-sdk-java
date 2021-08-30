package com.checkout.events;

import com.checkout.common.Resource;
import com.checkout.webhooks.EventType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EventResponse extends Resource {

    private String id;

    private EventType type;

    private String version;

    @SerializedName("created_on")
    private Instant createdOn;

    private Map<String, Object> data;

    private List<EventNotificationSummaryResponse> notifications;

    /**
     * Will be removed in a future version.
     */
    @Deprecated
    public String getType() {
        return type.getCode();
    }

}
