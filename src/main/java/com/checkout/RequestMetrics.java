package com.checkout;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class RequestMetrics {

    private String prevRequestId;

    private String requestId;

    private Long prevRequestDuration;

    public String toTelemetryHeader() {
        return String.format("{\"prev_request_id\":\"%s\",\"request_id\":\"%s\",\"prev_request_duration\":%d}",
                prevRequestId != null ? prevRequestId : "N/A",
                requestId != null ? requestId : "N/A",
                prevRequestDuration != null ? prevRequestDuration : 0);
    }
}
