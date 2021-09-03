package com.checkout.disputes.beta;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class Dispute extends Resource {

    private String id;

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("sub_entity_id")
    private String subEntityId;

    private DisputeCategory category;

    private DisputeStatus status;

    private Long amount;

    private String currency;

    @SerializedName("reason_code")
    private String reasonCode;

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("payment_action_id")
    private String paymentActionId;

    @SerializedName("payment_reference")
    private String paymentReference;

    @SerializedName("payment_arn")
    private String paymentArn;

    @SerializedName("payment_mcc")
    private String paymentMcc;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("evidence_required_by")
    private Instant evidenceRequiredBy;

    @SerializedName("received_on")
    private Instant receivedOn;

    @SerializedName("last_update")
    private Instant lastUpdate;

}
