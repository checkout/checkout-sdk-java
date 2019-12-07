package com.checkout.events;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventResponse extends Resource {
    private String id;
    private String type;
    private String version;
    private Instant createdOn;
    private Map<String, Object> data;
    private List<EventNotificationSummaryResponse> notifications;
}