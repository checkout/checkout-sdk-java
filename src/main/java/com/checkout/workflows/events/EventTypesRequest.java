package com.checkout.workflows.events;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public final class EventTypesRequest {
    
    @SerializedName("event_types")
    private List<String> eventTypes;
}
