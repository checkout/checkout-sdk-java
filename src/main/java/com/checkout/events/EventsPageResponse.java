package com.checkout.events;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public final class EventsPageResponse {

    @SerializedName("total_count")
    private int totalCount;

    private int limit;

    private int skip;

    private Instant from;

    private Instant to;

    private List<EventSummaryResponse> data;

}
