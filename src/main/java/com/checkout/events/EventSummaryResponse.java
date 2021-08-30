package com.checkout.events;

import com.checkout.common.Resource;
import com.checkout.webhooks.EventType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EventSummaryResponse extends Resource {

    private String id;

    private EventType type;

    @SerializedName("created_on")
    private Instant createdOn;

    /**
     * Will be removed in a future version.
     */
    @Deprecated
    public String getType() {
        return type.getCode();
    }

}
