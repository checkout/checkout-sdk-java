package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DisputesQueryFilter {

    @Size(min = 1, max = 250)
    private Integer limit;

    @Size
    private Integer skip;

    private Instant from;

    private Instant to;

    private String id;

    /**
     * One or more comma-separated statuses. This works like a logical OR operator
     */
    private String statuses;

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("payment_reference")
    private String paymentReference;

    @SerializedName("payment_arn")
    private String paymentArn;

    @SerializedName("this_channel_only")
    private Boolean thisChannelOnly;

    /**
     * Not available on Previous
     */

    /**
     * One or more comma-separated client entities. This works like a logical OR operator
     */
    @SerializedName("entity_ids")
    private String entityIds;

    /**
     * One or more comma-separated sub-entities. This works like a logical OR operator
     */
    @SerializedName("sub_entity_ids")
    private String subEntityIds;

    @SerializedName("payment_mcc")
    private String paymentMcc;

    @SerializedName("processing_channel_ids")
    private String processingChannelIds;

    @SerializedName("segment_ids")
    private String SegmentIds;

}
