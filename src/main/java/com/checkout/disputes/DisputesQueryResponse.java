package com.checkout.disputes;

import lombok.Data;

import java.util.List;

@Data
public final class DisputesQueryResponse {

    private Integer limit;
    private Integer skip;
    private String from;
    private String to;
    private String id;
    private String statuses;
    private String paymentId;
    private String paymentReference;
    private String paymentArn;
    private String thisChannelOnly;
    private Integer totalCount;
    private List<Dispute> data;

}
