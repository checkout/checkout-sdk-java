package com.checkout.events.previous;

import lombok.Data;

import java.time.Instant;

@Data
public final class AttemptSummaryResponse {

    private int statusCode;

    private String responseBody;

    private String sendMode;

    private Instant timestamp;

}
