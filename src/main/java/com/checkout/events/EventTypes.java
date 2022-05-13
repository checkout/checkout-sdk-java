package com.checkout.events;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class EventTypes {

    private String version;

    @SerializedName("event_types")
    private List<String> eventTypes;

}
