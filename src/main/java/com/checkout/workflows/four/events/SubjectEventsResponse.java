package com.checkout.workflows.four.events;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class SubjectEventsResponse {

    @SerializedName("data")
    private List<SubjectEvent> events;

}
