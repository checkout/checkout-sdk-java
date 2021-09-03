package com.checkout.disputes.beta;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class DisputeDetailsResponse extends Resource {

    private String id;

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("sub_entity_id")
    private String subEntityId;

    private DisputeCategory category;

    private Long amount;

    private String currency;

    @SerializedName("reason_code")
    private String reasonCode;

    private DisputeStatus status;

    @SerializedName("resolved_reason")
    private DisputeResolvedReason resolvedReason;

    @SerializedName("relevant_evidence")
    private List<DisputeRelevantEvidence> relevantEvidence;

    @SerializedName("evidence_required_by")
    private Instant evidenceRequiredBy;

    @SerializedName("received_on")
    private Instant receivedOn;

    @SerializedName("last_update")
    private Instant lastUpdate;

    private PaymentDispute payment;

}
