package com.checkout;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestMetrics {
    private String requestId;
    private Long prevRequestDuration;
    private String prevRequestId;

    public RequestMetrics(Long prevRequestDuration, String prevRequestId) {
        this.prevRequestDuration = prevRequestDuration;
        this.prevRequestId = prevRequestId;
    }
}