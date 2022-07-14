package com.checkout.disputes;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
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
     * Not available on Previous
     */

    @SerializedName("entity_ids")
    private String entityIds;

    @SerializedName("sub_entity_ids")
    private String subEntityIds;

    @SerializedName("payment_mcc")
    private String paymentMcc;

}
