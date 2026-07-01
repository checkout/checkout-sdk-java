package com.checkout.disputes;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
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

    private String reasonCode;

    private DisputeStatus status;

    private DisputeResolvedReason resolvedReason;

    private List<DisputeRelevantEvidence> relevantEvidence;

    private Instant evidenceRequiredBy;

    private Instant receivedOn;

    private Instant lastUpdate;

    private PaymentDispute payment;

    /**
     * Not available on Previous
     */

    private String entityId;

    private String subEntityId;

    private String reference;

    private Boolean isCeCandidate;

    private List<EvidenceList> evidenceList;

    private List<EvidenceBundle> evidenceBundle;

    private String segmentId;

}
