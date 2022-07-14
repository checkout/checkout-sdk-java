package com.checkout.disputes;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public final class Dispute extends Resource {

    private String id;

    private DisputeCategory category;

    private DisputeStatus status;

    private Long amount;

    private Currency currency;

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

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("evidence_required_by")
    private Instant evidenceRequiredBy;

    @SerializedName("received_on")
    private Instant receivedOn;

    @SerializedName("last_update")
    private Instant lastUpdate;

    /**
     * Not available on Previous
     */

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("sub_entity_id")
    private String subEntityId;

    @SerializedName("payment_mcc")
    private String paymentMcc;

}
