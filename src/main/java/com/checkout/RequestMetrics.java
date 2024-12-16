package com.checkout;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestMetrics {

    @SerializedName("prev_request_id")
    private String prevRequestId;

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("prev_request_duration")
    private Long prevRequestDuration;

    public String toTelemetryHeader() {
        return String.format("{\"prev_request_id\":\"%s\",\"request_id\":\"%s\",\"prev_request_duration\":%d}",
                prevRequestId != null ? prevRequestId : "N/A",
                requestId != null ? requestId : "N/A",
                prevRequestDuration != null ? prevRequestDuration : 0);
    }
}
