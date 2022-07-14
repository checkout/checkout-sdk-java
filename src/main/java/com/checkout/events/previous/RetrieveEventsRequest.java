package com.checkout.events.previous;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public final class RetrieveEventsRequest {

    private String paymentId;

    private String chargeId;

    private String trackId;

    private String reference;

    private Integer skip;

    private Integer limit;

    private Instant from;

    private Instant to;

}
