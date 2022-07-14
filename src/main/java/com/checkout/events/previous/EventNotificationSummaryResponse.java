package com.checkout.events.previous;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EventNotificationSummaryResponse extends Resource {

    private String id;

    private String url;

    private Boolean success;

}
