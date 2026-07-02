package com.checkout.workflows.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public final class EventTypesRequest {
    
    private List<String> eventTypes;
}
