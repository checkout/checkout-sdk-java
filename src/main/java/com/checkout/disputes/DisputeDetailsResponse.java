package com.checkout.disputes;

import com.checkout.common.Currency;
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

    private DisputeCategory category;

    private Long amount;

    private Currency currency;

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

    /**
     * Not available on Previous
     */

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("sub_entity_id")
    private String subEntityId;

    private String reference;

    @SerializedName("is_ce_candidate")
    private Boolean isCeCandidate;

    @SerializedName("evidence_list")
    private List<EvidenceList> evidenceList;

    @SerializedName("evidence_bundle")
    private List<EvidenceBundle> evidenceBundle;

    @SerializedName("segment_id")
    private String segmentId;

}
