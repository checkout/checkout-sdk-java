package com.checkout.disputes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class EvidenceList {

    private String file;

    private String text;

    private DisputeRelevantEvidence type;

    private String disputeId;

}
