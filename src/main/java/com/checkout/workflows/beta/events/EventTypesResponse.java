package com.checkout.workflows.beta.events;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class EventTypesResponse {

    private String id;

    @SerializedName("display_name")
    private String displayName;

    private String description;

    private List<Event> events;

}
