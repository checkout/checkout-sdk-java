package com.checkout.events;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventsPageResponse extends Resource {
    private int totalCount;
    private int limit;
    private int skip;
    private Instant from;
    private Instant to;
    private List<EventSummaryResponse> data;
}