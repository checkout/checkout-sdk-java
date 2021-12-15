package com.checkout.disputes;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class DisputeDetailsResponse extends Resource {

    private String id;
    private DisputeCategory category;
    private Double amount;
    private String currency;
    private String reasonCode;
    private DisputeStatus status;
    private DisputeResolvedReason resolvedReason;
    private List<DisputeRelevantEvidence> relevantEvidence;
    private String evidenceRequiredBy;
    private String receivedOn;
    private String lastUpdate;
    private PaymentDispute payment;

}
