package com.checkout.events;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventNotificationResponse extends Resource {
    private String id;
    private String url;
    private Boolean success;
    private String contentType;
    private List<AttemptSummaryResponse> attempts;
}
