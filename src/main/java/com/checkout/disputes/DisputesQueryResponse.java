package com.checkout.disputes;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class DisputesQueryResponse extends HttpMetadata {

    private Integer limit;

    private Integer skip;

    private Instant from;

    private Instant to;

    private String id;

    private String statuses;

    private String paymentId;

    private String paymentReference;

    private String paymentArn;

    private boolean thisChannelOnly;

    private Integer totalCount;

    private List<Dispute> data;

    /**
     * Not available on Previous
     */

    private String entityIds;

    private String subEntityIds;

    private String paymentMcc;

    private String processingChannelIds;

    private String SegmentIds;

}
