package com.checkout.workflows.events;

import lombok.Data;

import java.util.List;

@Data
public final class WorkflowEventTypes {

    private String id;

    private String displayName;

    private String description;

    private List<Event> events;

}
