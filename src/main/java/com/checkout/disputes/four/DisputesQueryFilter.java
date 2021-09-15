package com.checkout.disputes.four;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
public final class DisputesQueryFilter {

    @Size(min = 1, max = 250)
    private Integer limit;

    @Size
    private Integer skip;

    private Instant from;

    private Instant to;

    private String id;

    /** One or more comma-separated client entities. This works like a logical OR operator */
    @SerializedName("entity_ids")
    private String entityIds;

    /** One or more comma-separated sub-entities. This works like a logical OR operator */
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

}
