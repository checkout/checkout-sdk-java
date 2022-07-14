package com.checkout.events.previous;

import com.checkout.common.Resource;
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

    private String type;

    private String version;

    @SerializedName("created_on")
    private Instant createdOn;

    private Map<String, Object> data;

    private List<EventNotificationSummaryResponse> notifications;

}
