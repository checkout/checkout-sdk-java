package com.checkout.workflows.four.events;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class SubjectEventsResponse extends HttpMetadata {

    @SerializedName("data")
    private List<SubjectEvent> events;

}
