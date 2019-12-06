package com.checkout.events;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AttemptSummaryResponse extends Resource {
    private int statusCode;
    private String responseBody;
    private String retryMode;
    private Instant timestamp;
}