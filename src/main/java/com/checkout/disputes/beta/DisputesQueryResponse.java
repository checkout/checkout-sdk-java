package com.checkout.disputes.beta;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public final class DisputesQueryResponse {

    private Integer limit;

    private Integer skip;

    private Instant from;

    private Instant to;

    private String id;

    @SerializedName("entity_ids")
    private String entityIds;

    @SerializedName("sub_entity_ids")
    private String subEntityIds;

    /** One or more comma-separated statuses. This works like a logical OR operator */
    private String statuses;

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("payment_reference")
    private String paymentReference;

    @SerializedName("payment_arn")
    private String paymentArn;

    @SerializedName("payment_mcc")
    private String paymentMcc;

    @SerializedName("this_channel_only")
    private boolean thisChannelOnly;

    @SerializedName("total_count")
    private Integer totalCount;

    private List<Dispute> data;

}
