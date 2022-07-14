package com.checkout.events.previous;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class EventsPageResponse extends HttpMetadata {

    @SerializedName("total_count")
    private int totalCount;

    private int limit;

    private int skip;

    private Instant from;

    private Instant to;

    private List<EventSummaryResponse> data;

}
