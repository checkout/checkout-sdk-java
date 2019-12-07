package com.checkout.events;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventNotificationSummaryResponse extends Resource {
    private String id;
    private String url;
    private Boolean success;
}