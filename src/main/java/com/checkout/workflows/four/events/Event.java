package com.checkout.workflows.four.events;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class Event {

    private String id;

    @SerializedName("display_name")
    private String displayName;

    private String description;

}
