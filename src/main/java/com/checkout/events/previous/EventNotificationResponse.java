package com.checkout.events.previous;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EventNotificationResponse extends Resource {

    private String id;

    private String url;

    private Boolean success;

    @SerializedName("content_type")
    private String contentType;

    private List<AttemptSummaryResponse> attempts;

}
