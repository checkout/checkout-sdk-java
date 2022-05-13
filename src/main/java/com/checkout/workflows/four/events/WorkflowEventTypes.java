package com.checkout.workflows.four.events;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class WorkflowEventTypes {

    private String id;

    @SerializedName("display_name")
    private String displayName;

    private String description;

    private List<Event> events;

}
