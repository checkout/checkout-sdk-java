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

    /**
     * The dispute identifier.
     * [Optional]
     * ^(dsp)_(\w{22,26})$
     */
    private String id;

    /**
     * The reason for the dispute.
     * [Optional]
     */
    private DisputeCategory category;

    /**
     * The current status of the dispute.
     * [Optional]
     */
    private DisputeStatus status;

    /**
     * The disputed amount in the processing currency.
     * [Optional]
     */
    private Long amount;

    /**
     * The processing currency.
     * [Optional]
     */
    private Currency currency;

    /**
     * The card scheme reason code for the dispute.
     * [Optional]
     */
    @SerializedName("reason_code")
    private String reasonCode;

    /**
     * The payment identifier.
     * [Optional]
     */
    @SerializedName("payment_id")
    private String paymentId;

    /**
     * The payment action identifier.
     * [Optional]
     */
    @SerializedName("payment_action_id")
    private String paymentActionId;

    /**
     * The payment reference or order ID.
     * [Optional]
     */
    @SerializedName("payment_reference")
    private String paymentReference;

    /**
     * The acquirer reference number for the payment.
     * [Optional]
     */
    @SerializedName("payment_arn")
    private String paymentArn;

    /**
     * The payment method or card scheme.
     * [Optional]
     */
    @SerializedName("payment_method")
    private String paymentMethod;

    /**
     * The deadline by which evidence must be submitted.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    @SerializedName("evidence_required_by")
    private Instant evidenceRequiredBy;

    /**
     * The date and time the dispute was issued.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    @SerializedName("received_on")
    private Instant receivedOn;

    /**
     * The date and time of the last update to the dispute.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    @SerializedName("last_update")
    private Instant lastUpdate;

    /**
     * The reason the dispute was automatically resolved.
     * [Optional]
     * Enum: "rapid_dispute_resolution" "negative_amount" "already_refunded"
     */
    @SerializedName("resolved_reason")
    private DisputeResolvedReason resolvedReason;

    /**
     * Whether the dispute is eligible for Visa Compelling Evidence 3.0.
     * [Optional]
     */
    @SerializedName("is_ce_candidate")
    private Boolean isCeCandidate;

    // Not available on Previous API

    /**
     * The client entity linked to this dispute.
     * [Optional]
     */
    @SerializedName("entity_id")
    private String entityId;

    /**
     * The sub-entity linked to this dispute.
     * [Optional]
     */
    @SerializedName("sub_entity_id")
    private String subEntityId;

    /**
     * The processing channel linked to this dispute.
     * [Optional]
     */
    @SerializedName("processing_channel")
    private String processingChannel;

    /**
     * The business segment identifier.
     * [Optional]
     */
    @SerializedName("segment_id")
    private String segmentId;

    /**
     * The merchant category code for the payment.
     * [Optional]
     */
    @SerializedName("payment_mcc")
    private String paymentMcc;

}
