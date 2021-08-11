package com.checkout.disputes;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
public final class DisputesQueryFilter {

    @Size(min = 1, max = 250)
    private Integer limit;
    @Size
    private Integer skip;
    private String from;
    private String to;
    private String id;
    /** One or more comma-separated statuses. This works like a logical OR operator */
    private String statuses;
    private String paymentId;
    private String paymentReference;
    private String paymentArn;
    private String thisChannelOnly;

}
