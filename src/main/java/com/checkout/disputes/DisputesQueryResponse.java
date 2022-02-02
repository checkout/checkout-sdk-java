package com.checkout.disputes;

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

    private String statuses;

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("payment_reference")
    private String paymentReference;

    @SerializedName("payment_arn")
    private String paymentArn;

    @SerializedName("this_channel_only")
    private boolean thisChannelOnly;

    @SerializedName("total_count")
    private Integer totalCount;

    private List<Dispute> data;

    /**
     * Available only on Four
     */

    @SerializedName("entity_ids")
    private String entityIds;

    @SerializedName("sub_entity_ids")
    private String subEntityIds;

    @SerializedName("payment_mcc")
    private String paymentMcc;

}
